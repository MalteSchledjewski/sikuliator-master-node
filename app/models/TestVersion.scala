package models

import play.api.libs.json.{Json, Writes}

/**
  * Created by Malte on 24.01.2016.
  */
case class TestVersion(testVersionID :Long, test : Long, specification : String,timeCreated : java.sql.Timestamp ,parent : Option[Long])


object TestVersion {
  implicit val testVersionWrites = new Writes[TestVersion] {
    def writes(testVersion: TestVersion) =
      testVersion.parent match {
        case None =>
          Json.obj(
            "ID" -> testVersion.testVersionID,
            "test" -> testVersion.test,
            "spec" -> testVersion.specification,
            "timeCreated" -> testVersion.timeCreated.toString
          )
        case Some(parent: Long) =>
          Json.obj(
            "ID" -> testVersion.testVersionID,
            "test" -> testVersion.test,
            "parent" -> parent,
            "spec" -> testVersion.specification,
            "timeCreated" -> testVersion.timeCreated.toString
          )
      }

  }
}
