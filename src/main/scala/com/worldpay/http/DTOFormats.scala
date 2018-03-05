package com.worldpay.http

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

import scala.language.{higherKinds, implicitConversions, postfixOps}

trait DTOFormats {
  self: DefaultJsonProtocol ⇒
  implicit final protected def formatOfferDescription: RootJsonFormat[OfferDescription] = jsonFormat0(OfferDescription.apply)
  implicit final protected def formatOfferID: RootJsonFormat[OfferID] = jsonFormat1(OfferID.apply)
  implicit final protected def formatOffer: RootJsonFormat[Offer] = jsonFormat0(Offer.apply)
  implicit final protected def formatProcessed: RootJsonFormat[Processed] = jsonFormat0(Processed.apply)

}