package com.worldpay

import akka.actor.ActorRef
import com.worldpay.actors.RichActor

import scala.language.{higherKinds, implicitConversions, postfixOps}

package object http {
  def controller(bus: ActorRef): RichActor = new Controller(bus)
}
