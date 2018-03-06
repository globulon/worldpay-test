package com.worldpay

import akka.actor.ActorRef
import akka.util.Timeout
import com.worldpay.actors.RichActor

import scala.language.{higherKinds, implicitConversions, postfixOps}

package object http {
  def controller(bus: ActorRef)(implicit d: Timeout): RichActor = new Controller(bus)
}
