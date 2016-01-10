package models


import play.api.libs.json.{JsValue, JsArray, Json, Writes}

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by Malte on 10.01.2016.
  */
class Project(projectidC: Long, nameC: String) {
  val projectid = projectidC
  val name = nameC
  var flavours :Option[Seq[FlavourStub]] = None

  def getFlavours :Seq[FlavourStub] =
  {
    if (flavours.isEmpty )
      {
        flavours = Some(Await.result(ProjectsRepository.getFlavourStubs(projectid),Duration(1000, MILLISECONDS)))
//        flavours = Some(ProjectsRepository.getFlavourStubs(projectid).result(Duration(1000, MILLISECONDS)))
      }

    flavours.get
  }

}



object Project {
  implicit val projectWrites = new Writes[Project] {
    def writes(project: Project) = Json.obj(
      "ID" -> project.projectid,
      "name" -> project.name,
      "flavours" -> project.getFlavours.map( (flavour : FlavourStub) => Json.toJson(flavour)).foldLeft(JsArray()){
          (array : JsArray, stubJSON : JsValue) =>
            array.append(stubJSON)
        }
    )
  }
}
