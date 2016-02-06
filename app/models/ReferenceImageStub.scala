package models

import play.api.libs.json.{JsValue, JsArray, Json, Writes}

/**
  * Created by Malte on 06.02.2016.
  */
case class ReferenceImageStub(referenceimageid: Long, name: String)


object ReferenceImageStub {
  implicit val referenceImageWrites = new Writes[ReferenceImageStub] {
    def writes(referenceImage: ReferenceImageStub) = Json.obj(
      "ID" -> referenceImage.referenceimageid,
      "name" -> referenceImage.name
    )
  }
}
