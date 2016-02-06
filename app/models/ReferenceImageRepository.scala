package models

import java.io.FileInputStream

import models.ResultImageRepository._
import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.libs.Files
import slick.driver.JdbcProfile
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

/**
  * Created by Malte on 06.02.2016.
  */
object ReferenceImageRepository extends HasDatabaseConfig[JdbcProfile]{
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import driver.api._

  def listReferenceImages(projectId:Long) : Future[Option[Seq[ReferenceImageStub]]] =
  {
    val referenceImages: Future[Try[Seq[Tables.ReferenceimagesRow]]] = dbConfig.db.run(Tables.Referenceimages.filter(_.project === projectId).result.asTry)
    referenceImages.map[Option[Seq[ReferenceImageStub]]](
      {
        case Success(rows) =>
          Some(rows.toList.map((row: Tables.ReferenceimagesRow) => ReferenceImageStub(row.referenceimageid, row.name)))
        case Failure(e) =>
          None
      }
    )
  }



  def getReferenceImage(projectId : Long, referenceImageId : Long): Future[Option[ReferenceImage]] =
  {
    val referenceImageSqlResult: Future[Try[Seq[Tables.ReferenceimagesRow]]] = dbConfig.db.run(Tables.Referenceimages.filter(test => test.project === projectId && test.referenceimageid === referenceImageId).result.asTry)
    val possibleReferenceImage : Future[Option[ReferenceImageStub]] = referenceImageSqlResult.map[Option[ReferenceImageStub]](
      {
        case Success(rows) =>
          rows.headOption.flatMap[ReferenceImageStub]( (row : Tables.ReferenceimagesRow ) => Some(ReferenceImageStub(row.referenceimageid, row.name)))
        case Failure(e) =>
          None
      }
    )

    val eventualMaybeReferenceImageVersions : Future[Option[Seq[ReferenceImageVersion]]] = ReferenceImageVersionRepository.getReferenceImageVersions(projectId,referenceImageId)

    possibleReferenceImage.flatMap[Option[ReferenceImage]](
      {
        case None => Future{None}
        case Some(referenceImageStub : ReferenceImageStub) =>
          eventualMaybeReferenceImageVersions.map[Option[ReferenceImage]](
            {
              case None => None
              case Some(versionStubs :Seq[ReferenceImageVersion] ) =>
                Some(ReferenceImage(referenceImageId,referenceImageStub.name,versionStubs))
            }
          )
      }
    )
  }


  def createReferenceImage(projectId : Long,name : String, file:Files.TemporaryFile):Future[Option[ReferenceImage]] =
  {
    val is = new FileInputStream(file.file)
    val cnt = is.available
    val content :Array[Byte] = Array.ofDim[Byte](cnt)
    is.read(content)
    is.close()

    val action :DBIO[(Tables.ReferenceimagesRow,Tables.ReferenceimageversionsRow)] ={
      for{
        referenceImageId <- Tables.Referenceimages.map(
          referenceimages => (referenceimages.project,referenceimages.name)
        ).returning(Tables.Referenceimages.map(_.referenceimageid)) += (projectId,name)

        referenceImageVersionId <- Tables.Referenceimageversions.map(
          referenceimageversions => (referenceimageversions.referenceimage, referenceimageversions.parent, referenceimageversions.url)
        ).returning(Tables.Referenceimageversions.map(_.referenceimageversionid)) += (referenceImageId,None,"/referenceImage/")
        _ <- Tables.StorageReferenceimage += Tables.StorageReferenceimageRow(referenceImageVersionId, content)
        tmp <- Tables.Referenceimageversions.filter(_.referenceimageversionid === referenceImageVersionId).result.map(_.head)
        _ <- Tables.Referenceimageversions.insertOrUpdate(Tables.ReferenceimageversionsRow(tmp.referenceimageversionid,"/referenceImage/"+referenceImageVersionId,tmp.referenceimage,tmp.timecreated,tmp.parent))
        versionRow <- Tables.Referenceimageversions.filter(_.referenceimageversionid === referenceImageVersionId).result.map(_.head)
        row <- Tables.Referenceimages.filter(_.referenceimageid === referenceImageId).result.map(_.head)
      } yield (row,versionRow)
    }


    dbConfig.db.run(action.transactionally.asTry).map(
      {
        case Success(result) =>
          val referenceImage = result._1
          val referenceImageVersion = result._2
          Some(ReferenceImage(referenceImage.referenceimageid, referenceImage.name, Seq[ReferenceImageVersion](ReferenceImageVersion(referenceImageVersion.referenceimageversionid,referenceImageVersion.url,referenceImageVersion.timecreated,referenceImageVersion.parent))))
        case Failure(e) =>
          None
      }
    )

  }

}
