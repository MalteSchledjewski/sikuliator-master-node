package models

import play.api.libs.json.{Json, Writes}

/**
  * Created by Malte on 06.02.2016.
  */
case class ResultImage(id:Long,name:String,url:String,timeCreated: java.sql.Timestamp)


object ResultImage {
  implicit val testVersionWrites = new Writes[ResultImage] {
    def writes(resultImage: ResultImage) =
      Json.obj(
        "ID" -> resultImage.id,
        "name" -> resultImage.name,
        "url" -> resultImage.url,
        "timeCreated" -> resultImage.timeCreated.toString
      )
  }
}