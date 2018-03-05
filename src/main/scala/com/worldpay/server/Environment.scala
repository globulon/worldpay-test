package com.worldpay.server

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.util.Timeout

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._
import scala.language.{higherKinds, implicitConversions, postfixOps}

trait Environment {
  protected implicit val system: ActorSystem = ActorSystem("OfferingSystem")
  protected implicit val materializer: ActorMaterializer = ActorMaterializer()
  final protected implicit def executionContext: ExecutionContextExecutor = system.dispatcher
  final protected implicit def timeout: Timeout = 1 second
}