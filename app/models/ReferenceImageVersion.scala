package models

import play.api.libs.json.{JsValue, JsArray, Json, Writes}

/**
  * Created by Malte on 06.02.2016.
  */
case class ReferenceImageVersion(referenceimageversionid: Long, url: String, timeCreated: java.sql.Timestamp, parent: Option[Long] = None)

object ReferenceImageVersion {
  implicit val referenceImageVersionWrites = new Writes[ReferenceImageVersion] {
    def writes(referenceImageVersion: ReferenceImageVersion) = if(referenceImageVersion.parent.isDefined){
      Json.obj(
        "ID" -> referenceImageVersion.referenceimageversionid,
        "url" -> referenceImageVersion.url,
        "timeCreated" -> referenceImageVersion.timeCreated.toString,
        "parent" -> referenceImageVersion.parent.get
      )
    }
    else
    {
      Json.obj(
        "ID" -> referenceImageVersion.referenceimageversionid,
        "url" -> referenceImageVersion.url,
        "timeCreated" -> referenceImageVersion.timeCreated.toString
      )
    }


  }
}
