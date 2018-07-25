name := "spark-almighty"
organization in ThisBuild := "com.playbuzz"
scalaVersion in ThisBuild := "2.11.8"
version in ThisBuild := "0.1.0-SNAPSHOT"

// PROJECTS

lazy val global = project
  .in(file("."))
  .settings(settings)
  .aggregate(
    common,
    loader,
    enricher,
    transformer
  )

lazy val common = project
  .settings(
    name := "common",
    settings,
    libraryDependencies ++= commonDependencies
  )

lazy val loader = project
  .settings(
    name := "loader",
    settings,
    assemblySettings,
    libraryDependencies ++= commonDependencies ++ Seq(
      )
  )
  .dependsOn(
    common,
    enricher
  )

lazy val enricher = project
  .settings(
    name := "enricher",
    settings,
    assemblySettings,
    libraryDependencies ++= commonDependencies ++ Seq(
      )
  )
  .dependsOn(
    common,
    transformer
  )

lazy val transformer = project
  .settings(
    name := "transformer",
    settings,
    assemblySettings,
    libraryDependencies ++= commonDependencies ++ Seq(
      )
  )
  .dependsOn(
    common
  )

// DEPENDENCIES

lazy val dependencies =
  new {
    // Enable when you have logging
    // val scalaLoggingV   = "3.7.2"
    // val slf4jV          = "1.7.25"

    // Enable when you have tests
    // val scalatestV      = "3.0.4"
    // val scalacheckV     = "1.13.5"
    val sparkV = "2.3.1"

    // Enable when you have logging
    // val scalaLogging   = "com.typesafe.scala-logging" %% "scala-logging"           % scalaLoggingV
    // val slf4j          = "org.slf4j"                  % "jcl-over-slf4j"           % slf4jV
    val sparkCore = "org.apache.spark" %% "spark-core" % sparkV
    val sparkSql = "org.apache.spark" %% "spark-sql" % sparkV

    // Enable when you have tests
    // val scalatest      = "org.scalatest"              %% "scalatest"               % scalatestV
    // val scalacheck     = "org.scalacheck"             %% "scalacheck"              % scalacheckV
  }

lazy val commonDependencies = Seq(
  // Enable when you logging
  //  dependencies.scalaLogging,
  //  dependencies.slf4j,
  dependencies.sparkCore,
  dependencies.sparkSql
  // Enable when you tests
  // dependencies.scalatest % "test",
  // dependencies.scalacheck % "test"
)

// SETTINGS

lazy val settings =
  commonSettings ++
    wartremoverSettings ++
    scalafmtSettings

lazy val compilerOptions = Seq(
  "-unchecked",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-deprecation",
  "-encoding",
  "utf8"
)

lazy val commonSettings = Seq(
  scalacOptions ++= compilerOptions,
  resolvers ++= Seq(
    "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  )
)

lazy val wartremoverSettings = Seq(
  wartremoverWarnings in (Compile, compile) ++= Warts.allBut(Wart.Throw)
)

lazy val scalafmtSettings =
  Seq(
    scalafmtOnCompile := true,
    scalafmtTestOnCompile := true,
    scalafmtVersion := "1.2.0"
  )

lazy val assemblySettings = Seq(
  assemblyJarName in assembly := name.value + "-" + version.value + ".jar",
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case _                             => MergeStrategy.first
  }
)
