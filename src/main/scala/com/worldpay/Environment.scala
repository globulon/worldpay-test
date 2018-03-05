package com.worldpay

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor
import scala.language.{higherKinds, implicitConversions, postfixOps}

trait Environment {
  protected implicit val system: ActorSystem = ActorSystem("my-system")
  protected implicit val materializer: ActorMaterializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  final protected implicit def executionContext: ExecutionContextExecutor = system.dispatcher
}