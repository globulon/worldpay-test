import sbt._

import scala.language.{higherKinds, implicitConversions, postfixOps}

trait TestDependencies { self: Versions =>
  final protected def akkaTestKit: ModuleID = "com.typesafe.akka" %% "akka-http-testkit" % akkaTestKitVer % Test withSources()
  final protected def scalaTest: ModuleID = "org.scalatest" %% "scalatest" % scalaTestVer % Test withSources()
}