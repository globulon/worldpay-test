package com.worldpay.server

import akka.actor.ActorRef

import scala.language.{higherKinds, implicitConversions, postfixOps}

case class Application(controller: ActorRef)
