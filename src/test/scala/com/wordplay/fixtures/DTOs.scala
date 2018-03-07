package com.wordplay.fixtures

import spray.json.{JsArray, JsNumber, JsObject, JsString}

import scala.language.{higherKinds, implicitConversions, postfixOps}

trait DTOs {
  final protected def offerCreation = JsObject(
    "price" → JsNumber(250),
    "descriptions" → JsArray(JsString("ABC"), JsString("EFG")),
    "duration" → JsNumber(3600)
  )

  final protected def offerUpdate = JsObject(
    "price" → JsNumber(400),
    "descriptions" → JsArray(JsString("VWX"))
  )
}
