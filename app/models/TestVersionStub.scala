package models

import play.api.libs.json.{Json, Writes}

/**
  * Created by Malte on 20.01.2016.
  */
case class TestVersionStub(ID :Long,parentID :Option[Long])

object TestVersionStub {
  implicit val testVersionStubWrites = new Writes[TestVersionStub] {
    def writes(test: TestVersionStub) = if(test.parentID.isDefined){
      Json.obj(
        "ID" -> test.ID,
        "parent" -> test.parentID.get
      )
    }
    else
      {
        Json.obj(
          "ID" -> test.ID
        )
      }
  }
}
