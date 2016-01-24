package models

import play.api.Play
import play.api.db.slick.{HasDatabaseConfig, DatabaseConfigProvider}
import play.api.libs.json.Json
import slick.driver.JdbcProfile

import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

/**
  * Created by Malte on 10.01.2016.
  */
object ProjectsRepository extends HasDatabaseConfig[JdbcProfile]{
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import driver.api._

  def getAllProjectStubs: Future[Seq[ProjectStub]] =
    {
      val projects: Future[Seq[Tables.ProjectsRow]] = dbConfig.db.run(Tables.Projects.result)
      projects.flatMap( (rows : Seq[Tables.ProjectsRow]) =>
        Future {
          rows.toList.map((row: Tables.ProjectsRow) => ProjectStub(row.projectid, row.name))
        }
      )
    }

  def createProject(projectName: String):Future[ProjectStub] =
  {
      val projectId = dbConfig.db.run((Tables.Projects returning Tables.Projects.map(_.projectid)) += Tables.ProjectsRow(0, projectName))
      projectId.flatMap((projectId) => Future{
        ProjectStub(projectId ,projectName)
      })
  }

  def getProject(projectId: Long): Future[Option[Project]] = {
    val projects: Future[Seq[Tables.ProjectsRow]] = dbConfig.db.run(Tables.Projects.filter(_.projectid === projectId).result)
    projects.flatMap((rows: Seq[Tables.ProjectsRow]) =>
      Future {
        rows.headOption.map((row: Tables.ProjectsRow) => {
          new Project(row.projectid, row.name,Await.result(FlavourRepository.getFlavourStubs(projectId),Duration(1000, MILLISECONDS)).get)
        })
      }
    )
  }
}
