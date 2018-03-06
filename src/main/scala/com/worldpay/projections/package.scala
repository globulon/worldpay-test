package com.worldpay

import akka.actor.{ActorRef, ActorSystem, Props}

import scala.language.{higherKinds, implicitConversions, postfixOps}

package object projections {
  def bus(implicit sys: ActorSystem): ActorRef =
    sys.actorOf(Props(new DomainBus), "Bus")
}
