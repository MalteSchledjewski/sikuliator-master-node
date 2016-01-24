package models
import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import slick.driver.JdbcProfile

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.{Await, Future}

import scala.util.Try
import scala.util.Success
import scala.util.Failure
import scala.concurrent.duration._
/**
  * Created by Malte on 20.01.2016.
  */
object TestRepository extends HasDatabaseConfig[JdbcProfile]{
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  import driver.api._


  def getTestStubs(projectId : Long): Future[Option[Seq[TestStub]]] =
  {
    val flavours: Future[Try[Seq[Tables.TestsRow]]] = dbConfig.db.run(Tables.Tests.filter(_.project === projectId).result.asTry)
    flavours.map[Option[Seq[TestStub]]](
      {

        case Success(rows) =>
          Some(rows.toList.map((row: Tables.TestsRow) => TestStub(row.testid, row.name)))
        case Failure(e) =>
          None
      }
    )
  }


  def createTest(projectId : Long,name : String):Future[Option[Long]] =
  {
    val insertAction = (Tables.Tests.map(
      testTable => (testTable.project,testTable.name)
    ) returning Tables.Tests.map(_.testid)) += (projectId,name)
    val testId = dbConfig.db.run(insertAction.asTry)
    testId.map{
      case Success(id) =>
        Some(id)
      case Failure(e) =>
        None
    }
  }

  def getTest(projectId : Long, testId : Long): Future[Option[Test]] =
  {
    val testSqlResult: Future[Try[Seq[Tables.TestsRow]]] = dbConfig.db.run(Tables.Tests.filter(test => test.project === projectId && test.testid === testId).result.asTry)
    val test : Future[Option[TestStub]] = testSqlResult.map[Option[TestStub]](
      {

        case Success(rows) =>
          rows.headOption.flatMap[TestStub]( (row : Tables.TestsRow ) => Some(TestStub(row.testid, row.name)))
        case Failure(e) =>
          None
      }
    )

    val testVersion : Future[Option[Seq[TestVersionStub]]] = TestVersionRepository.getTestVersionStubs(projectId,testId)

    test.flatMap[Option[Test]](
      {
        case None => Future{None}
        case Some(testStub : TestStub) =>
          testVersion.map[Option[Test]](
            {
              case None => None
              case Some(versionStubs :Seq[TestVersionStub] ) =>
                Some(Test(testId,testStub.name,versionStubs))
            }
          )
      }
    )
  }




}
