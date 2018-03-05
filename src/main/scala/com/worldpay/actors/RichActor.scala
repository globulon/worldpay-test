package com.worldpay.actors

import akka.actor.{Actor, ActorLogging, ActorPath, ActorSelection, ActorSystem}

import scala.language.{higherKinds, implicitConversions, postfixOps}

trait RichActor extends Actor with ActorLogging {
  final protected def select(p: String): ActorSelection = context.actorSelection(p)

  final protected def select(p: ActorPath): ActorSelection = context.actorSelection(p)

  final protected def system: ActorSystem = context.system

  final protected def name: String = s"""${getClass.getSimpleName}-${self.path.name}"""

  final protected def die: Unit = context stop self

}
