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
    val testVersionStubs: Future[Try[Seq[Tables.TestversionsRow]]] = dbConfig.db.run(Tables.Testversions.filter(_.test === testId).result.asTry)
    testVersionStubs.map[Option[Seq[TestVersionStub]]](
      {

        case Success(rows) =>
          Some(rows.toList.map((row: Tables.TestversionsRow) => TestVersionStub(row.testversionid, row.parent)))
        case Failure(e) =>
          None
      }
    )
  }


  def getTestVersion(testVersionId : Long): Future[Option[TestVersion]] =
  {
    val testVersionResult: Future[Try[Seq[Tables.TestversionsRow]]] = dbConfig.db.run(Tables.Testversions.filter(_.testversionid === testVersionId).result.asTry)
    testVersionResult.map[Option[TestVersion]](
      {
        case Success(rows) =>
          rows.headOption.map(
            (row: Tables.TestversionsRow) => TestVersion(row.testversionid,row.test,row.specification,row.timecreated,row.parent)
          )
        case Failure(e) =>
          None
      }
    )
  }


  def getTestVersionsForFlavour(flavourId : Long): Future[Option[Seq[(Long,TestVersion)]]] =
  {
    val eventualJoinRows = dbConfig.db.run(Tables.Flavourtotestversion.join(Tables.Tests).on(_.testid === _.testid).join(Tables.Testversions).on(_._1.testversionid === _.testversionid).filter(_._1._1.flavourid === flavourId).result.asTry)
    eventualJoinRows.map[Option[Seq[(Long,TestVersion)]]](
      {
        case Success(rows) =>
          Some(rows.toList.map((row) => (row._1._2.testid ,TestVersion(row._2.testversionid,row._1._2.testid, row._2.specification, row._2.timecreated,row._2.parent))))
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
