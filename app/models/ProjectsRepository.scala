package models

import play.api.Play
import play.api.db.slick.{HasDatabaseConfig, DatabaseConfigProvider}
import play.api.libs.json.Json
import slick.driver.JdbcProfile

import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

/**
  * Created by Malte on 10.01.2016.
  */
class ProjectsRepository extends HasDatabaseConfig[JdbcProfile]{
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
        val id:Long = projectId
        ProjectStub(id ,projectName)
      })
  }
}
