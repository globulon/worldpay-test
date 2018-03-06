package com.worldpay.server

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.util.Timeout
import com.worldpay.{http, projections}

import scala.language.{higherKinds, implicitConversions, postfixOps}

trait Bootstrap {
  final protected def controller(implicit sys: ActorSystem, d: Timeout): ActorRef =
    sys.actorOf(Props(http.controller(projections.bus)), "Controller")
}
