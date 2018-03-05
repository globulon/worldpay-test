import com.typesafe.sbt.packager.archetypes.JavaServerAppPackaging
import sbt.Keys._
import sbt._

import scala.language.{higherKinds, implicitConversions, postfixOps}

object ApplicationBuild extends Build with BuildSettings
  with Resolvers with Versions with Dependencies with TestDependencies {
  lazy val mailingService: Project = Project(
    "worldpay-offer",
    file("."),
    settings = buildSettings ++
      Seq(resolvers ++= repos) ++
      Seq(libraryDependencies ++= Seq(scalaz, akkaHTTP, akkaTestKit, scalaTesting % "test", akkaLogging, akkaJson))
  ).settings(allJavaOptions:_*).enablePlugins(JavaServerAppPackaging)
}