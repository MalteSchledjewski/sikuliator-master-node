package models


import play.api.libs.json.{JsValue, JsArray, Json, Writes}

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by Malte on 10.01.2016.
  */
case class Project(projectid: Long, name: String, flavours :Seq[FlavourStub])



object Project {
  implicit val projectWrites = new Writes[Project] {
    def writes(project: Project) = Json.obj(
      "ID" -> project.projectid,
      "name" -> project.name,
      "flavours" -> project.flavours.map( (flavour : FlavourStub) => Json.toJson(flavour)).foldLeft(JsArray()){
          (array : JsArray, stubJSON : JsValue) =>
            array.append(stubJSON)
        }
    )
  }
}
