package com.worldpay

import akka.http.scaladsl.Http

import scala.io.StdIn
import scala.language.{higherKinds, implicitConversions, postfixOps}


object Server extends Routes with Environment {
  def main(args: Array[String]) {
    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}