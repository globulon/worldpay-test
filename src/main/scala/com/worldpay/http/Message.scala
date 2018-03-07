package com.worldpay.http

import akka.actor.ActorRef

import scala.language.{higherKinds, implicitConversions, postfixOps}

sealed trait Message
case class Push(offer: OfferCreation) extends Message
case class Get(byId: OfferID) extends Message
case class GetAll() extends Message
case class Update(byId: OfferID, offer: OfferUpdate) extends Message
case class Delete(byId: OfferID) extends Message
case class Cancel(byId: OfferID) extends Message
case class Received(any: Any, rec: ActorRef)