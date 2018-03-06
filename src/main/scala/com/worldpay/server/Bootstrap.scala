package com.worldpay.server

import akka.actor.{ActorRef, ActorSystem, Props}
import com.worldpay.{http, projections}

import scala.language.{higherKinds, implicitConversions, postfixOps}

trait Bootstrap {
  final protected def controller(implicit sys: ActorSystem): ActorRef =
    sys.actorOf(Props(http.controller(projections.bus)), "Controller")
}
