package models

import play.api.libs.json.{Writes, Json}

/**
  * Created by Malte on 10.01.2016.
  */
case class FlavourStub(flavourid: Long, name: String)

object FlavourStub {
  implicit val flavourStubWrites = new Writes[FlavourStub] {
    def writes(flavourStub: FlavourStub) = Json.obj(
      "ID" -> flavourStub.flavourid,
      "name" -> flavourStub.name
    )
  }
}



