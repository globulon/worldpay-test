package com.worldpay.http

import akka.actor.ActorRef
import com.worldpay.actors.RichActor

import scala.language.{higherKinds, implicitConversions, postfixOps}

final class Controller(bus: ActorRef) extends RichActor {
  override def receive: Receive = {
    case any ⇒ sender ! any
  }
}

