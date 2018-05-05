name := "hello"

version := "0.1"

val commonSettings = Seq(
  scalaVersion := "2.12.6",
  scalacOptions ++= Seq("-language:experimental.macros"),
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % scalaVersion.value
  )
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .aggregate(share, hello)

lazy val share = (project in file("share"))
  .settings(commonSettings)

lazy val hello = (project in file("hello"))
  .settings(commonSettings)
  .dependsOn(share)

