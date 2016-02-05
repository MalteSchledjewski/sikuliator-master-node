package models

import play.api.libs.json.{Json, Writes}

case class SequenceVersion(sequenceVersionID :Long, sequence : Long, specification : String, timeCreated : java.sql.Timestamp, parent : Option[Long])

/**
  * Created by Malte on 05.02.2016.
  */
object SequenceVersion {
  implicit val sequenceVersionWrites = new Writes[SequenceVersion] {
    def writes(sequenceVersion: SequenceVersion) =
      sequenceVersion.parent match {
        case None =>
          Json.obj(
            "ID" -> sequenceVersion.sequenceVersionID,
            "sequence" -> sequenceVersion.sequence,
            "spec" -> sequenceVersion.specification,
            "timeCreated" -> sequenceVersion.timeCreated.toString
          )
        case Some(parent: Long) =>
          Json.obj(
            "ID" -> sequenceVersion.sequenceVersionID,
            "sequence" -> sequenceVersion.sequence,
            "parent" -> parent,
            "spec" -> sequenceVersion.specification,
            "timeCreated" -> sequenceVersion.timeCreated.toString
          )
      }

  }
}
