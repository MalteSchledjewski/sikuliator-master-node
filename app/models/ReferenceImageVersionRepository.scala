package models

import java.io.FileInputStream

import models.TestVersionRepository._
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
object ReferenceImageVersionRepository extends HasDatabaseConfig[JdbcProfile]{
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  import driver.api._


  def getReferenceImageVersions(projectId : Long, referenceImageId : Long): Future[Option[Seq[ReferenceImageVersion]]] =
  {
    val eventualMaybeReferenceImageVersions: Future[Try[Seq[Tables.ReferenceimageversionsRow]]] = dbConfig.db.run(Tables.Referenceimageversions.filter(_.referenceimage === referenceImageId).result.asTry)
    eventualMaybeReferenceImageVersions.map[Option[Seq[ReferenceImageVersion]]](
      {
        case Success(rows) =>
          Some(rows.toList.map((row: Tables.ReferenceimageversionsRow) => ReferenceImageVersion(row.referenceimageversionid, row.url, row.timecreated,row.parent)))
        case Failure(e) =>
          None
      }
    )
  }


  def getReferenceImageVersion(referenceImageVersionId : Long): Future[Option[ReferenceImageVersion]] =
  {
    val testVersionResult: Future[Try[Seq[Tables.ReferenceimageversionsRow]]] = dbConfig.db.run(Tables.Referenceimageversions.filter(_.referenceimageversionid === referenceImageVersionId).result.asTry)
    testVersionResult.map[Option[ReferenceImageVersion]](
      {
        case Success(rows) =>
          rows.headOption.map(
            (row: Tables.ReferenceimageversionsRow) => ReferenceImageVersion(row.referenceimageversionid, row.url, row.timecreated,row.parent)
          )
        case Failure(e) =>
          None
      }
    )
  }

  def createReferenceImageVersion(referenceImageId :Long, parent : Option[Long], file:Files.TemporaryFile) : Future[Option[ReferenceImageVersion]] =
  {
    val is = new FileInputStream(file.file)
    val cnt = is.available
    val content :Array[Byte] = Array.ofDim[Byte](cnt)
    is.read(content)
    is.close()

    val action :DBIO[Tables.ReferenceimageversionsRow] ={
      for{
        referenceImageVersionId <- Tables.Referenceimageversions.map(
                    referenceimageversions => (referenceimageversions.referenceimage, referenceimageversions.parent, referenceimageversions.url)
                  ).returning(Tables.Referenceimageversions.map(_.referenceimageversionid)) += (referenceImageId,parent,"/resultImage/")
        _ <- Tables.StorageReferenceimage += Tables.StorageReferenceimageRow(referenceImageVersionId, content)
        tmp <- Tables.Referenceimageversions.filter(_.referenceimageversionid === referenceImageVersionId).result.map(_.head)
        _ <- Tables.Referenceimageversions.insertOrUpdate(Tables.ReferenceimageversionsRow(tmp.referenceimageversionid,"/resultImage/"+referenceImageVersionId,tmp.referenceimage,tmp.timecreated,tmp.parent))
        row <- Tables.Referenceimageversions.filter(_.referenceimageversionid === referenceImageVersionId).result.map(_.head)
      } yield row
    }


    dbConfig.db.run(action.transactionally.asTry).map(
      {
        case Success(result) =>
          Some(ReferenceImageVersion(result.referenceimageversionid,result.url,result.timecreated,result.parent))
        case Failure(e) =>
          None
      }
    )
  }


}
