package com.wordplay.fixtures

import akka.actor.{ActorRef, ActorRefFactory, Props}

import scala.language.{higherKinds, implicitConversions, postfixOps}

trait Stunts {
  final protected def echo(sys: ActorRefFactory, response: Any ⇒ Any = { any ⇒ any }): ActorRef = sys.actorOf(Props(new Echo(response)))
}
