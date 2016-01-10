package models

import play.api.libs.json.{Writes, Json}

/**
  * Created by Malte on 10.01.2016.
  */
case class ProjectStub(projectid: Long, name: String)

object ProjectStub {
  implicit val projectStubWrites = new Writes[ProjectStub] {
    def writes(projectStub: ProjectStub) = Json.obj(
      "ID" -> projectStub.projectid,
      "name" -> projectStub.name
    )
  }
}
