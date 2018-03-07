package com.worldpay.http

import akka.actor.ActorRef
import akka.pattern.{ask, pipe}
import akka.util.Timeout
import com.worldpay.actors.RichActor
import com.worldpay.projections._
import com.worldpay.translations._

import scala.language.{higherKinds, implicitConversions, postfixOps}

protected[http] final class Controller(val bus: ActorRef)(implicit val t: Timeout) extends RichActor with OfferTranslations with Translations {
  import context.dispatcher

  override def receive: Receive = {
    case Push(OfferCreation(price, descs, d)) ⇒
      val snd = sender
      (bus ? CreateOffer(price, descs, d)) map { Received(_, snd) } pipeTo self
    case Received(Offers(all), snd) ⇒
      snd ! Offers(all).translateTo[Found]
    case Update(OfferID(value), OfferUpdate(p, desc)) ⇒
      bus ! UpdateOffer(value, p, desc)
    case Delete(OfferID(value)) ⇒
      bus ! DeleteOffer(value)
    case Get(OfferID(value)) ⇒
      val snd = sender
      (bus ? GetOffer(value)) map { Received(_, snd) } pipeTo self
    case GetAll() ⇒
      val snd = sender
      (bus ? GetOffers()) map { Received(_, snd) } pipeTo self
  }
}