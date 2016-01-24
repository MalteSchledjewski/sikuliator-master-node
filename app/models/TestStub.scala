package models

import play.api.libs.json.{Json, Writes}

/**
  * Created by Malte on 20.01.2016.
  */
case class TestStub(ID :Long, name : String)

object TestStub {
  implicit val testStubWrites = new Writes[TestStub] {
    def writes(test: TestStub) = Json.obj(
      "ID" -> test.ID,
      "name" -> test.name
    )
  }
}
