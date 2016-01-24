package models

import play.api.libs.json.{JsValue, JsArray, Json, Writes}



case class Test(testId: Long, name: String, versions :Seq[TestVersionStub])
/**
  * Created by Malte on 24.01.2016.
  */
object Test {
  implicit val projectWrites = new Writes[Test] {
    def writes(test: Test) = Json.obj(
      "ID" -> test.testId,
      "name" -> test.name,
      "versions" -> test.versions.map((testVersionStub : TestVersionStub) => Json.toJson(testVersionStub)).foldLeft(JsArray()){
        (array : JsArray, stubJSON : JsValue) =>
          array.append(stubJSON)
      }
    )
  }
}
