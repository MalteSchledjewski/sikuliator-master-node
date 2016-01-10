package controllers

import models.{ProjectStub, ProjectsRepository, Tables}
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

  val projectsRepo = new ProjectsRepository()

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def projects = Action.async {
    implicit request =>
      {
        val projectStubs = projectsRepo.getAllProjectStubs
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
      projectsRepo.createProject(projectName).flatMap(
        (stub : ProjectStub) => Future{
          Ok (Json.prettyPrint(Json.toJson(stub)))
        }
      )
    }

  }

}
