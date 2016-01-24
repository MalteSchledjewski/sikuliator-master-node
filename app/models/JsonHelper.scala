package models

import play.api.libs.json.{Writes, JsValue, JsArray, Json}

/**
  * Created by Malte on 11.01.2016.
  */




object JsonHelper {
  def concatAsJsonArray(inputs: Seq[JsValue]):JsArray = inputs.foldLeft(JsArray()){
    (array : JsArray, value : JsValue) =>
      array.append(value)
  }
}

