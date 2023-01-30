ThisBuild / organization := "com.example"
ThisBuild / scalaVersion := "3.2.0"
val http4sVersion = "1.0.0-M37"
val doobieVersion = "1.0.0-RC2"

lazy val root = (project in file(".")).settings(
  name := "ce3-server",
  libraryDependencies ++= Seq(
    // "core" module - IO, IOApp, schedulers
    // This pulls in the kernel and std modules automatically.
    "org.typelevel" %% "cats-effect" % "3.4.5",
    // concurrency abstractions and primitives (Concurrent, Sync, Async etc.)
    "org.typelevel" %% "cats-effect-kernel" % "3.4.5",
    // standard "effect" library (Queues, Console, Random etc.)
    "org.typelevel" %% "cats-effect-std" % "3.4.5",
    "org.http4s" %% "http4s-dsl" % http4sVersion,
    "org.http4s" %% "http4s-ember-server" % http4sVersion,
    "org.http4s" %% "http4s-circe" % http4sVersion,
    "org.tpolecat" % "doobie-core_3" % doobieVersion,
    "mysql" % "mysql-connector-java" % "8.0.32",
    "org.tpolecat" %% "doobie-hikari" % doobieVersion,
    "com.typesafe" % "config" % "1.4.2",
    "ch.qos.logback" % "logback-classic" % "1.4.5",
    "org.typelevel" %% "log4cats-core" % "2.5.0",
    "org.typelevel" %% "log4cats-slf4j" % "2.5.0",
    "org.tpolecat" %% "doobie-postgres"  % "1.0.0-RC2",
  )
)
