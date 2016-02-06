package models

import play.api.libs.json.{JsValue, JsArray, Json, Writes}

/**
  * Created by Malte on 06.02.2016.
  */
case class ReferenceImage(referenceimageid: Long, name: String, versions :Seq[ReferenceImageVersion])


object ReferenceImage {
  implicit val referenceImageWrites = new Writes[ReferenceImage] {
    def writes(referenceImage: ReferenceImage) = Json.obj(
      "ID" -> referenceImage.referenceimageid,
      "name" -> referenceImage.name,
      "versions" -> referenceImage.versions.map((referenceImageVersion : ReferenceImageVersion) => Json.toJson(referenceImageVersion)).foldLeft(JsArray()){
        (array : JsArray, stubJSON : JsValue) =>
          array.append(stubJSON)
      }
    )
  }
}
