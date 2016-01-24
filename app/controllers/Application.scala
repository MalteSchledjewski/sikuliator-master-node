package controllers

import models._
import play.api._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.libs.json.{JsArray, _}
import play.api.mvc.{Action, Controller, Result}
import slick.driver.JdbcProfile
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future


class Application extends Controller with HasDatabaseConfig[JdbcProfile]{
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)


  def projects = Action.async {
    implicit request =>
      {
        val projectStubs = ProjectsRepository.getAllProjectStubs
        projectStubs.flatMap( (stubs : Seq[ProjectStub]) => Future{
          val jsonStubs = stubs.toList.map((stub : ProjectStub) => Json.toJson(stub))
          Ok (Json.prettyPrint(JsonHelper.concatAsJsonArray(jsonStubs)))
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



  def listFlavours(projectId : Long) = Action.async
  {
    implicit request => {
      FlavourRepository.getFlavourStubs(projectId).flatMap(
        (stubs : Option[Seq[FlavourStub]]) => Future{
          stubs match
          {
            case Some(flavours: Seq[FlavourStub]) =>
            {
              val jsonStubs = flavours.toList.map((stub : FlavourStub) => Json.toJson(stub))
              Ok (Json.prettyPrint(JsonHelper.concatAsJsonArray(jsonStubs)))
            }
            case None => BadRequest
          }
        }
      )
    }
  }

  def createFlavour(projectId : Long,flavourName : String) = Action.async
  {
    implicit request => {
      FlavourRepository.createFlavour(projectId, flavourName).flatMap(
        (stub : Option[FlavourStub]) => Future{
          stub match
            {
            case Some(flavour: FlavourStub) => Ok(Json.prettyPrint(Json.toJson(flavour)))
            case None => BadRequest
          }
        }
      )
    }
  }


  def listTests(projectId : Long) = Action.async
  {
    implicit request => {
      TestRepository.getTestStubs(projectId).flatMap(
        (stubs : Option[Seq[TestStub]]) => Future{
          stubs match
          {
            case Some(tests: Seq[TestStub]) =>
            {
              val jsonStubs = tests.toList.map((stub : TestStub) => Json.toJson(stub))
              Ok (Json.prettyPrint(JsonHelper.concatAsJsonArray(jsonStubs)))
            }
            case None => BadRequest
          }
        }
      )
    }
  }

  def getTest(projectId : Long, testId : Long) = Action.async
  {
    implicit request => {
      TestRepository.getTest(projectId,testId).flatMap(
        (test : Option[Test]) => Future{
          test match
          {
            case Some(t) => Ok (Json.prettyPrint(Json.toJson(t)))
            case None => NotFound
          }
        }
      )
    }
  }

}
