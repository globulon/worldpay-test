package com.worldpay.http

import scala.language.{higherKinds, implicitConversions, postfixOps}

sealed trait Message
case class Push(offer: OfferCreation) extends Message
case class Get(byId: OfferID) extends Message
case class GetAll() extends Message
case class Put(byId: OfferID, offer: OfferCreation) extends Message
case class Delete(byId: OfferID) extends Message
case class Created(offer: Offer) extends Message