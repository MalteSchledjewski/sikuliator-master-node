package models

import play.api.libs.json.{Json, Writes}

/**
  * Created by Malte on 06.02.2016.
  */
case class TestExecutable(id:Long,name:String,url:String,timeCreated: java.sql.Timestamp)


object TestExecutable {
  implicit val testExecutbaleVersionWrites = new Writes[TestExecutable] {
    def writes(testExecutable: TestExecutable) =
      Json.obj(
        "ID" -> testExecutable.id,
        "name" -> testExecutable.name,
        "url" -> testExecutable.url,
        "timeCreated" -> testExecutable.timeCreated.toString
      )
  }
}