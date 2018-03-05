package com.worlplay.service

import com.worldpay.http.{OfferDescription, OfferID}

import scala.language.{higherKinds, implicitConversions, postfixOps}

sealed trait Message
case class Push(offer: OfferDescription) extends Message
case class Get(byId: OfferID) extends Message
case class GetAll() extends Message
case class Put(byId: OfferID, offer: OfferDescription) extends Message
case class Delete(byId: OfferID) extends Message
