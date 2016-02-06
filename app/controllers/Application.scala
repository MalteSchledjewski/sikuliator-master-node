package controllers

import models._
import play.api._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.libs.Files
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

  def createTest(projectId : Long,name : String, spec:String) = Action.async
  {
    implicit request => {
      TestRepository.createTest(projectId, name).flatMap[Result]
          {
            case Some(testId: Long) =>
              TestVersionRepository.createTestVersion(testId,None,spec).flatMap[Result](
                {
                  case None => Future{InternalServerError}
                  case Some(testVersionId) =>
                    TestRepository.getTest(projectId,testId).map[Result](
                      {
                        case None => InternalServerError
                        case Some(test : Test) =>
                          Ok (Json.prettyPrint(Json.toJson(test)))
                      }
                    )
                }
              )
            case None => Future{BadRequest}
          }
        }


  }


  def createTestVersion(projectId : Long, testId :Long, parent : Option[Long], spec : String) = Action.async
  {
    implicit request => {
      TestVersionRepository.createTestVersion(testId,parent,spec).flatMap[Result](
        {
            case Some(testVersionId: Long) =>
              TestVersionRepository.getTestVersion(testVersionId).map[Result](
                {
                  case Some(testVersion : TestVersion) =>
                    Ok(Json.prettyPrint(Json.toJson(testVersion)))
                  case None =>
                    InternalServerError
                }
              )
            case None => Future{BadRequest}
          }
      )
    }
  }


  def getTestVersion(projectId: Long,testId :Long,testVersionId : Long) = Action.async
  {
    implicit request => {
      TestVersionRepository.getTestVersion(testVersionId).flatMap(
        (testVersion : Option[TestVersion]) => Future{
          testVersion match
          {
            case Some(t) => Ok (Json.prettyPrint(Json.toJson(t)))
            case None => NotFound
          }
        }
      )
    }
  }

  def createNewTestVersion(projectId : Long, testId :Long, parent : Long, spec : String) = createTestVersion(projectId,testId,Some(parent),spec)



//-------------------------


  def getBinaryResultImage(id : Long) = Action.async
  {
    implicit request => {
      BinaryRepository.getResultImageBinary(id).map[Result](
        {
          case Some(x : Array[Byte]) => Ok(x).as("image/png")
          case None => NotFound
        }

      )
    }
  }



  def getBinaryReferenceImage(id : Long) = Action.async
  {
    implicit request => {
      BinaryRepository.getReferenceImageBinary(id).map[Result](
        {
          case Some(x : Array[Byte]) => Ok(x).as("image/png")
          case None => NotFound
        }

      )
    }
  }


  def getTestExecutableBinary(id : Long) = Action.async
  {
    implicit request => {
      BinaryRepository.getTestExecutableBinary(id).map[Result](
        {
          case Some(x : Array[Byte]) => Ok(x).as("application/zip")
          case None => NotFound
        }

      )
    }
  }

//-------------------------



  def listSequences(projectId : Long) = Action.async
  {
    implicit request => {
      SequenceRepository.getSequenceStubs(projectId).flatMap(
        (stubs : Option[Seq[SequenceStub]]) => Future{
          stubs match
          {
            case Some(sequences: Seq[SequenceStub]) =>
            {
              val jsonStubs = sequences.toList.map((stub : SequenceStub) => Json.toJson(stub))
              Ok (Json.prettyPrint(JsonHelper.concatAsJsonArray(jsonStubs)))
            }
            case None => BadRequest
          }
        }
      )
    }
  }

  def getSequence(projectId : Long, sequenceId : Long) = Action.async
  {
    implicit request => {
      SequenceRepository.getSequence(projectId,sequenceId).flatMap(
        (sequence : Option[ReusableSequence]) => Future{
          sequence match
          {
            case Some(t) => Ok (Json.prettyPrint(Json.toJson(t)))
            case None => NotFound
          }
        }
      )
    }
  }

  def createSequence(projectId : Long,name : String, spec:String) = Action.async
  {
    implicit request => {
      SequenceRepository.createSequence(projectId, name).flatMap[Result]
        {
          case Some(sequenceId: Long) =>
            SequenceVersionRepository.createSequenceVersion(sequenceId,None,spec).flatMap[Result](
              {
                case None => Future{InternalServerError}
                case Some(testVersionId) =>
                  SequenceRepository.getSequence(projectId,sequenceId).map[Result](
                    {
                      case None => InternalServerError
                      case Some(sequence : ReusableSequence) =>
                        Ok (Json.prettyPrint(Json.toJson(sequence)))
                    }
                  )
              }
            )
          case None => Future{BadRequest}
        }
    }


  }


  def createSequenceVersion(projectId : Long, sequenceId :Long, parent : Option[Long], spec : String) = Action.async
  {
    implicit request => {
      SequenceVersionRepository.createSequenceVersion(sequenceId,parent,spec).flatMap[Result](
        {
          case Some(sequenceVersionId: Long) =>
            SequenceVersionRepository.getSequenceVersion(sequenceVersionId).map[Result](
              {
                case Some(sequenceVersion : SequenceVersion) =>
                  Ok(Json.prettyPrint(Json.toJson(sequenceVersion)))
                case None =>
                  InternalServerError
              }
            )
          case None => Future{BadRequest}
        }
      )
    }
  }


  def getSequenceVersion(projectId: Long, sequenceId :Long, sequenceVersionId : Long) = Action.async
  {
    implicit request => {
      SequenceVersionRepository.getSequenceVersion(sequenceVersionId).flatMap(
        (sequenceVersion : Option[SequenceVersion]) => Future{
          sequenceVersion match
          {
            case Some(t) => Ok (Json.prettyPrint(Json.toJson(t)))
            case None => NotFound
          }
        }
      )
    }
  }

  def createNewSequenceVersion(projectId : Long, sequenceId :Long, parent : Long, spec : String) = createSequenceVersion(projectId,sequenceId,Some(parent),spec)


//-------------------------


  def listResultImages(projectId : Long) = Action.async
  {
    implicit request => {
      ResultImageRepository.listResultImages(projectId).flatMap(
        (stubs : Option[Seq[ResultImage]]) => Future{
          stubs match
          {
            case Some(resultImages: Seq[ResultImage]) =>
            {
              val jsonStubs = resultImages.toList.map((stub : ResultImage) => Json.toJson(stub))
              Ok (Json.prettyPrint(JsonHelper.concatAsJsonArray(jsonStubs)))
            }
            case None => BadRequest
          }
        }
      )
    }
  }

  def getResultImage(projectId : Long, resultImageId : Long) = Action.async
  {
    implicit request => {
      ResultImageRepository.getResultImage(projectId,resultImageId).flatMap(
        (maybeResultImage : Option[ResultImage]) => Future{
          maybeResultImage match
          {
            case Some(t) => Ok (Json.prettyPrint(Json.toJson(t)))
            case None => NotFound
          }
        }
      )
    }
  }

  def addResultImage(projectId : Long) = Action.async(parse.multipartFormData)
  {
    implicit request => {
        request.body.file("resultImage") match {
          case None => Future{BadRequest("no image")}
          case Some(file) =>

            val f :Files.TemporaryFile  = file.ref
            request.body.asFormUrlEncoded.find(entry => entry._1 == "name").map(entry => entry._2.head) match
              {
              case None => Future{BadRequest("no name")}
              case Some(name:String) =>
                ResultImageRepository.addResultImages(projectId,name,f).map(
                  {
                    case Some(t) => Ok (Json.prettyPrint(Json.toJson(t)))
                    case None => InternalServerError
                  }
                )
            }
        }
      }
    }

  //-------------------------


  def listTestExectuables(projectId : Long) = Action.async
  {
    implicit request => {
      TestExecutableRepository.listTestExecutables(projectId).flatMap(
        (maybeTestExecutables : Option[Seq[TestExecutable]]) => Future{
          maybeTestExecutables match
          {
            case Some(testExecutables: Seq[TestExecutable]) =>
            {
              val jsonStubs = testExecutables.toList.map((testExecutable : TestExecutable) => Json.toJson(testExecutable))
              Ok (Json.prettyPrint(JsonHelper.concatAsJsonArray(jsonStubs)))
            }
            case None => BadRequest
          }
        }
      )
    }
  }

  def getTestExectuable(projectId : Long, testExectuableId : Long) = Action.async
  {
    implicit request => {
      TestExecutableRepository.getTestExecutable(projectId,testExectuableId).flatMap(
        (maybeTestExecutable : Option[TestExecutable]) => Future{
          maybeTestExecutable match
          {
            case Some(t) => Ok (Json.prettyPrint(Json.toJson(t)))
            case None => NotFound
          }
        }
      )
    }
  }

  def addTestExectuable(projectId : Long) = Action.async(parse.multipartFormData)
  {
    implicit request => {
      request.body.file("testExecutable") match {
        case None => Future{BadRequest("no executable")}
        case Some(file) =>
          val f :Files.TemporaryFile  = file.ref
          request.body.asFormUrlEncoded.find(entry => entry._1 == "name").map(entry => entry._2.head) match
          {
            case None => Future{BadRequest("no name")}
            case Some(name:String) =>
              TestExecutableRepository.addTestExecutables(projectId,name,f).map(
                {
                  case Some(t) => Ok (Json.prettyPrint(Json.toJson(t)))
                  case None => InternalServerError
                }
              )
          }
      }
    }
  }

  //-------------------------


  def listReferenceImages(projectId : Long) = Action.async
  {
    implicit request => {
      ReferenceImageRepository.listReferenceImages(projectId).flatMap(
        (stubs : Option[Seq[ReferenceImageStub]]) => Future{
          stubs match
          {
            case Some(referenceImageStubs: Seq[ReferenceImageStub]) =>
            {
              val jsonStubs = referenceImageStubs.toList.map((stub : ReferenceImageStub) => Json.toJson(stub))
              Ok (Json.prettyPrint(JsonHelper.concatAsJsonArray(jsonStubs)))
            }
            case None => BadRequest
          }
        }
      )
    }
  }

  def getReferenceImage(projectId : Long, referenceImageId : Long) = Action.async
  {
    implicit request => {
      ReferenceImageRepository.getReferenceImage(projectId,referenceImageId).flatMap(
        (test : Option[ReferenceImage]) => Future{
          test match
          {
            case Some(t) => Ok (Json.prettyPrint(Json.toJson(t)))
            case None => NotFound
          }
        }
      )
    }
  }

  def createReferenceImage(projectId : Long) = Action.async(parse.multipartFormData)
  {
    implicit request => {
      request.body.file("referenceImage") match {
        case None => Future{BadRequest("no image")}
        case Some(file) =>

          val f :Files.TemporaryFile  = file.ref
          request.body.asFormUrlEncoded.find(entry => entry._1 == "name").map(entry => entry._2.head) match
          {
            case None => Future{BadRequest("no name")}
            case Some(name:String) =>
              ReferenceImageRepository.createReferenceImage(projectId, name,f).map[Result]
                {
                  case Some(referenceImage: ReferenceImage) =>
                    Ok (Json.prettyPrint(Json.toJson(referenceImage)))
                  case None => InternalServerError
                }

          }
      }
    }


  }


  def createReferenceImageVersion(projectId : Long, referenceImageId :Long, parent : Option[Long]) = Action.async(parse.multipartFormData)
  {
    implicit request => {
      request.body.file("referenceImage") match {
        case None => Future{BadRequest("no image")}
        case Some(file) =>

          val f :Files.TemporaryFile  = file.ref
          request.body.asFormUrlEncoded.find(entry => entry._1 == "name").map(entry => entry._2.head) match
          {
            case None => Future{BadRequest("no name")}
            case Some(name:String) =>

              ReferenceImageVersionRepository.createReferenceImageVersion(referenceImageId,parent,f).map(
                {
                  case Some(t) => Ok (Json.prettyPrint(Json.toJson(t)))
                  case None => InternalServerError
                }
              )
          }
      }
    }
  }


  def getReferenceImageVersion(projectId: Long, referenceImageId :Long, referenceImageVersionId : Long) = Action.async
  {
    implicit request => {
      ReferenceImageVersionRepository.getReferenceImageVersion(referenceImageVersionId).flatMap(
        (maybeReferenceImageVersion : Option[ReferenceImageVersion]) => Future{
          maybeReferenceImageVersion match
          {
            case Some(t) => Ok (Json.prettyPrint(Json.toJson(t)))
            case None => NotFound
          }
        }
      )
    }
  }

  def createNewReferenceImageVersion(projectId : Long, referenceImageId :Long, parent : Long) = createReferenceImageVersion(projectId,referenceImageId,Some(parent))






}
