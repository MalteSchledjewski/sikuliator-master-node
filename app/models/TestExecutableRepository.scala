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

object TestExecutableRepository extends HasDatabaseConfig[JdbcProfile]{
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import driver.api._

  def listTestExecutables(projectId:Long) : Future[Option[Seq[TestExecutable]]] =
  {
    val eventualMaybeExecutables: Future[Try[Seq[Tables.TestexecutablesRow]]] = dbConfig.db.run(Tables.Testexecutables.filter(_.project === projectId).result.asTry)
    eventualMaybeExecutables.map[Option[Seq[TestExecutable]]](
      {
        case Success(rows) =>
          Some(rows.toList.map((row: Tables.TestexecutablesRow) => TestExecutable(row.testexecutableid, row.name,row.url,row.timecreated)))
        case Failure(e) =>
          None
      }
    )
  }

  def getTestExecutable(projectId:Long, resultImageId:Long) : Future[Option[TestExecutable]] =
  {
    val eventualMaybeExecutable: Future[Try[Seq[Tables.TestexecutablesRow]]] = dbConfig.db.run(Tables.Testexecutables.filter(testexecutables => testexecutables.project === projectId && testexecutables.testexecutableid === resultImageId).result.asTry)
    eventualMaybeExecutable.map[Option[TestExecutable]](
      {
        case Success(rows) =>
          rows.headOption.map(
            (row: Tables.TestexecutablesRow) => TestExecutable(row.testexecutableid, row.name,row.url,row.timecreated)
          )
        case Failure(e) =>
          None
      }
    )
  }

  def addTestExecutables(projectId:Long, name:String, file:Files.TemporaryFile) : Future[Option[TestExecutable]] =
  {
    val is = new FileInputStream(file.file)
    val cnt = is.available
    val content :Array[Byte] = Array.ofDim[Byte](cnt)
    is.read(content)
    is.close()

    val action :DBIO[Tables.TestexecutablesRow] ={
      for{
        testExecutableId <- (
          Tables.Testexecutables.map(
            ResultimagesTable => (ResultimagesTable.project, ResultimagesTable.name, ResultimagesTable.url)
          )
            returning Tables.Testexecutables.map(_.testexecutableid)
          ) += (projectId,name,"/testExecutable/")
        _ <- Tables.StorageTestexecutable += Tables.StorageTestexecutableRow(testExecutableId, content)
        tmp <- Tables.Testexecutables.filter(_.testexecutableid === testExecutableId).result.map(_.head)
        _ <- Tables.Testexecutables.insertOrUpdate(Tables.TestexecutablesRow(tmp.testexecutableid,tmp.name,"/testExecutable/"+testExecutableId,tmp.project,tmp.timecreated))
        row <- Tables.Testexecutables.filter(_.testexecutableid === testExecutableId).result.map(_.head)
      } yield row
    }

    dbConfig.db.run(action.transactionally.asTry).map(
      {
        case Success(row) =>
          Some(TestExecutable(row.testexecutableid, row.name,row.url,row.timecreated))
        case Failure(e) =>
          None
      }
    )

  }

}
