package models

import java.io.FileInputStream

import play.api.libs.Files
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import slick.driver.JdbcProfile

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

import play.api.Logger

/**
  * Created by Malte on 06.02.2016.
  */
object ResultImageRepository extends HasDatabaseConfig[JdbcProfile]{
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import driver.api._

  def listResultImages(projectId:Long) : Future[Option[Seq[ResultImage]]] =
  {
    val sequences: Future[Try[Seq[Tables.ResultimagesRow]]] = dbConfig.db.run(Tables.Resultimages.filter(_.project === projectId).result.asTry)
    sequences.map[Option[Seq[ResultImage]]](
      {

        case Success(rows) =>
          Some(rows.toList.map((row: Tables.ResultimagesRow) => ResultImage(row.resultimageid, row.name,row.url,row.timecreated)))
        case Failure(e) =>
          None
      }
    )
  }

  def getResultImages(projectId:Long, resultImageId:Long) : Future[Option[ResultImage]] =
  {
    val sequences: Future[Try[Seq[Tables.ResultimagesRow]]] = dbConfig.db.run(Tables.Resultimages.filter(resultImage => resultImage.project === projectId && resultImage.resultimageid === resultImageId).result.asTry)
    sequences.map[Option[ResultImage]](
      {

        case Success(rows) =>
          rows.headOption.map(
            (row: Tables.ResultimagesRow) => ResultImage(row.resultimageid, row.name,row.url,row.timecreated)
          )
        case Failure(e) =>
          None
      }
    )
  }

  def addResultImages(projectId:Long, name:String, file:Files.TemporaryFile) : Future[Option[ResultImage]] =
  {
    val is = new FileInputStream(file.file)
    val cnt = is.available
    val content :Array[Byte] = Array.ofDim[Byte](cnt)
    is.read(content)
    is.close()

    val action :DBIO[Tables.ResultimagesRow] ={
      for{
        resultImageId <- (
          Tables.Resultimages.map(
            ResultimagesTable => (ResultimagesTable.project, ResultimagesTable.name, ResultimagesTable.url)
          )
            returning Tables.Resultimages.map(_.resultimageid)
          ) += (projectId,name,"/resultImage/")
        _ <- Tables.StorageResultimage += Tables.StorageResultimageRow(resultImageId, content)
        tmp <- Tables.Resultimages.filter(_.resultimageid === resultImageId).result.map(_.head)
        _ <- Tables.Resultimages.insertOrUpdate(Tables.ResultimagesRow(tmp.resultimageid,tmp.name,"/resultImage/"+resultImageId,tmp.project,tmp.timecreated))
        row <- Tables.Resultimages.filter(_.resultimageid === resultImageId).result.map(_.head)
      } yield row
    }

    dbConfig.db.run(action.transactionally.asTry).map(
      {
        case Success(row) =>
          Some(ResultImage(row.resultimageid, row.name,row.url,row.timecreated))
        case Failure(e) =>
          None
      }
    )

  }

}
