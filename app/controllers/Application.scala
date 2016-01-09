package controllers

import play.api._
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.{JsString, JsValue, JsObject, JsArray}
import play.api.mvc._
import play.api.db.slick.HasDatabaseConfig
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.Action
import models.Tables
import play.api.mvc.Controller
import slick.driver.JdbcProfile
import slick.driver.JdbcProfile
import play.api.mvc.Result
import scala.concurrent.Future
import slick.driver.PostgresDriver
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._


class Application extends Controller with HasDatabaseConfig[JdbcProfile]{
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import driver.api._

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }


//  def projects = Action {
//    Ok(Tables.Projects.)
//  }


  def f1(rows : Seq[Tables.ProjectsRow]) : Future[Result] = Future {
    val text = rows.foldLeft (JsArray()) {
      (combined: JsArray, row: Tables.ProjectsRow) =>
        combined.append( JsObject(Map("ID" -> JsString(row.projectid.toString), "name" -> JsString(row.name))))
    }
    Ok (Json.prettyPrint(text))
  }

  def projects = Action.async {
    implicit request =>
      {
        val projects: Future[Seq[Tables.ProjectsRow]] = dbConfig.db.run(Tables.Projects.result)
        projects.flatMap( f1)
//    projects.onSuccess(case rows :Seq[Tables.ProjectsRow] =>
//    Ok(views.html.index(rows.toList.fold(""){
//        (combined : String, row : Tables.ProjectsRow) =>
//          combined + row.projectid.toString + " " + row.name + "\n"
//      }
//    )))
      }
//    projects.map(_.foreach {Tables.ProjectsRow r => })
//    projects.map(case Tables.ProjectsRow r =>   Ok(views.html.index(r.id.toString + " " + name.toString))
//    projects.map(project => Ok(views.html.index(project.toString())))
  }
}
