package com.worldpay.server

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.Http
import com.worldpay.http

import scala.io.StdIn
import scala.language.{higherKinds, implicitConversions, postfixOps}

trait Bootstrap { self: Environment ⇒
  final protected def controller(implicit sys: ActorSystem): ActorRef =
    system.actorOf(Props(http.controller), "Controller")
}

object Server extends Routes with Environment with Bootstrap {
  def main(args: Array[String]) {
    val bindingFuture = Http().bindAndHandle(routes(Application(controller)), "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}