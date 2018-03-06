import sbt._

import scala.language.{higherKinds, implicitConversions, postfixOps}

trait Dependencies { self: Versions =>
  final protected def scalaz: ModuleID = "org.scalaz" %% "scalaz-core" % scalazVersion withSources()
  final protected def scalaTesting: ModuleID = "org.scalatest" %% "scalatest" % scalaTestVer withSources()

  final protected def config: ModuleID = "com.typesafe" % "config" % configVersion withSources()

  final protected def akkaLogging: ModuleID = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion withSources()
  final protected def akkaActor: ModuleID = "com.typesafe.akka" %% "akka-actor" % akkaVersion withSources()
  final protected def akkaRemote: ModuleID = "com.typesafe.akka" %% "akka-remote" % akkaVersion withSources()
  final protected def akkaCluster: ModuleID = "com.typesafe.akka" %% "akka-cluster" % akkaVersion withSources()
  final protected def akkaPersistence: ModuleID = "com.typesafe.akka" %% "akka-persistence" % akkaVersion withSources()
  final protected def akkaClusterTools: ModuleID = "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion withSources()
  final protected def akkaClusterMetrics: ModuleID = "com.typesafe.akka" %% "akka-cluster-metrics" % akkaVersion withSources()
  final protected def akkaClusterSharding: ModuleID = "com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion withSources()
  final protected def levelDb: ModuleID = "org.iq80.leveldb" % "leveldb" % levelDbVer withSources()
  final protected def levelDbJNI: ModuleID = "org.fusesource.leveldbjni" % "leveldbjni-all" % levelDbJNIVer withSources()
  final protected def akkaHTTP: ModuleID  = "com.typesafe.akka" %% "akka-http" % akkaHTTPVersion withSources()
  final protected def akkaJson: ModuleID  = "com.typesafe.akka" %% "akka-http-spray-json" % akkaHTTPVersion withSources()
}