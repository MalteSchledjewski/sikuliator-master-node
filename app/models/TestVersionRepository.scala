package models

import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import slick.dbio.DBIOAction
import slick.driver.JdbcProfile

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

/**
  * Created by Malte on 20.01.2016.
  */
object TestVersionRepository  extends HasDatabaseConfig[JdbcProfile]{
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  import driver.api._


  def getTestVersionStubs(projectId : Long,testId : Long): Future[Option[Seq[TestVersionStub]]] =
  {
    val flavours: Future[Try[Seq[Tables.TestversionsRow]]] = dbConfig.db.run(Tables.Testversions.filter(_.test === testId).result.asTry)
    flavours.map[Option[Seq[TestVersionStub]]](
      {

        case Success(rows) =>
          Some(rows.toList.map((row: Tables.TestversionsRow) => TestVersionStub(row.testversionid, row.parent)))
        case Failure(e) =>
          None
      }
    )
  }

  def createTestVersion(testId :Long, parent : Option[Long], spec : String) : Future[Option[Long]] =
  {
    val insertAction = (Tables.Testversions.map(
        testVersionTable => (testVersionTable.test,testVersionTable.parent,testVersionTable.specification)
      )
      returning Tables.Testversions.map(_.testversionid)) += (testId,parent,spec)
    dbConfig.db.run(insertAction.asTry).map(
      {
        case Success(result) =>
            Some(result)
        case Failure(e) =>
          None
      }
    )
  }

}
