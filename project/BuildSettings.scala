import sbt.Keys._
import sbt._

import scala.language.{higherKinds, implicitConversions, postfixOps}

trait BuildSettings { self: Versions ⇒
  final protected def buildSettings: Seq[Setting[_]] = Defaults.coreDefaultSettings ++ Seq(
    organization  := "com.worldpay",
    version       := appVer,
    scalaVersion  := scalaVer,
    scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-target:jvm-1.8"),
    ivyValidate   := false,
    fork in Test  := true,
    shellPrompt   := {  Project.extract(_).currentRef.project + "> " }
  )

  final protected def allJavaOptions: Seq[sbt.Def.SettingsDefinition] = Seq(
    javaOptions in Test += "-Dconfig.file=src/test/resources/test.conf"
  )
}
