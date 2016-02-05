package models

import play.api.libs.json.{Json, Writes}

/**
  * Created by Malte on 05.02.2016.
  */
case class SequenceVersionStub(ID :Long,parentID :Option[Long])

object SequenceVersionStub {
  implicit val testVersionStubWrites = new Writes[SequenceVersionStub] {
    def writes(test: SequenceVersionStub) = if(test.parentID.isDefined){
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
