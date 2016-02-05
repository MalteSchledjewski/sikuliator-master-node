package models

import play.api.libs.json.{Json, Writes}

/**
  * Created by Malte on 05.02.2016.
  */
case class SequenceStub(ID :Long, name : String)

object SequenceStub {
  implicit val sequenceStubWrites = new Writes[SequenceStub] {
    def writes(sequence: SequenceStub) = Json.obj(
      "ID" -> sequence.ID,
      "name" -> sequence.name
    )
  }
}

