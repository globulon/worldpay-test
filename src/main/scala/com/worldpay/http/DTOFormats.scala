package com.worldpay.http

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

import spray.json.{DefaultJsonProtocol, JsString, JsValue, RootJsonFormat, deserializationError}

import scala.language.{higherKinds, implicitConversions, postfixOps}

trait DTOFormats {
  self: DefaultJsonProtocol ⇒
  implicit protected final def dateTimeFormat: RootJsonFormat[java.time.LocalDateTime] = new RootJsonFormat[LocalDateTime] {
    override def read(json: JsValue): LocalDateTime = json match {
      case JsString(dateTime) ⇒ LocalDateTime.parse(dateTime)
      case _                  ⇒ deserializationError("Improper LocalDateTime")
    }

    override def write(dateTime: LocalDateTime): JsValue = JsString(ISO_LOCAL_DATE_TIME.format(dateTime))
  }
  implicit final protected def formatOfferDescription: RootJsonFormat[OfferCreation] = jsonFormat3(OfferCreation.apply)
  implicit final protected def formatOfferUpdate: RootJsonFormat[OfferUpdate] = jsonFormat2(OfferUpdate.apply)
  implicit final protected def formatOfferID: RootJsonFormat[OfferID] = jsonFormat1(OfferID.apply)
  implicit final protected def formatOffer: RootJsonFormat[Offer] = jsonFormat5(Offer.apply)
  implicit final protected def formatProcessed: RootJsonFormat[Processed] = jsonFormat0(Processed.apply)
  implicit final protected def formatFound: RootJsonFormat[Found] = jsonFormat1(Found.apply)
  implicit final protected def formatError: RootJsonFormat[Error] = jsonFormat1(Error.apply)
}