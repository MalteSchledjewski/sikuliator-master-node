package controllers

import models.{Project, ProjectStub, ProjectsRepository, Tables}
import play.api._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.libs.json.{JsArray, _}
import play.api.mvc.{Action, Controller, Result}
import slick.driver.JdbcProfile
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future


class Application extends Controller with HasDatabaseConfig[JdbcProfile]{
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import driver.api._

//  val projectsRepo = new ProjectsRepository()

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def projects = Action.async {
    implicit request =>
      {
        val projectStubs = ProjectsRepository.getAllProjectStubs
        projectStubs.flatMap( (stubs : Seq[ProjectStub]) => Future{
          val jsonStubs = stubs.toList.map((stub : ProjectStub) => Json.toJson(stub))
          Ok (Json.prettyPrint(jsonStubs.foldLeft( JsArray()){
            (array : JsArray, stubJSON : JsValue) =>
                array.append(stubJSON)
          }))
        })
      }

  }

  def createProject(projectName : String) = Action.async
  {
    implicit request => {
      ProjectsRepository.createProject(projectName).flatMap(
        (stub : ProjectStub) => Future{
          Ok (Json.prettyPrint(Json.toJson(stub)))
        }
      )
    }
  }


  def getProject(projectId : Long) = Action.async
  {
    implicit request => {
      ProjectsRepository.getProject(projectId).flatMap(
        (project : Option[Project]) => Future{
          project match
            {
            case Some(t) => Ok (Json.prettyPrint(Json.toJson(t)))
            case None => NotFound
          }

        }
      )
    }
  }


}
