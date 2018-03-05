package com.worldpay.http

import com.worldpay.actors.RichActor

import scala.language.{higherKinds, implicitConversions, postfixOps}

final class Controller extends RichActor {
  override def receive: Receive = {
    case any ⇒ sender ! any
  }
}

