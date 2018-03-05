package com.wordplay.fixtures

import com.worldpay.actors.RichActor

import scala.language.{higherKinds, implicitConversions, postfixOps}

class Echo(response: Any ⇒ Any) extends RichActor {
  override def receive: Receive = { case a ⇒ sender ! response(a)}
}
