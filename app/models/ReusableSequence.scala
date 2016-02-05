package models

/**
  * Created by Malte on 05.02.2016.
  */

import play.api.libs.json.{JsValue, JsArray, Json, Writes}



case class ReusableSequence(sequenceId: Long, name: String, versions :Seq[SequenceVersionStub])
/**
  * Created by Malte on 24.01.2016.
  */
object ReusableSequence {
  implicit val sequenceWrites = new Writes[ReusableSequence] {
    def writes(sequence: ReusableSequence) = Json.obj(
      "ID" -> sequence.sequenceId,
      "name" -> sequence.name,
      "versions" -> sequence.versions.map((sequenceVersionStub : SequenceVersionStub) => Json.toJson(sequenceVersionStub)).foldLeft(JsArray()){
        (array : JsArray, stubJSON : JsValue) =>
          array.append(stubJSON)
      }
    )
  }
}


