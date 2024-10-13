name := "scala3-metaprog"
description := "scala 3 metaprog"
version := "0.0.1"

val sharedSettings = Seq(
  // scalacOptions ++= Seq("-Xfatal-warnings, -deprecation"),
  scalacOptions ++= Seq(
    "-source:future",
    // "-language:experimental.modularity",
    "-language:experimental.namedTuples",
    "-Xkind-projector:underscores",
    "-deprecation",
    "-Wunused:all"
  ),
  libraryDependencies ++= Seq(
    "dev.zio" %% "zio-test" % Versions.ZioVersion % Test
  ),
  scalaVersion := Versions.Scala_3_5_1,
  testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
)

lazy val backend = project
  .in(file("backend"))
  .settings(
    sharedSettings,
    Compile / run / mainClass := Some("base.Main"),
    libraryDependencies ++= Seq(
    )
  )
  .dependsOn(shared)

lazy val shared = project
  .in(file("shared"))
  .settings(
    sharedSettings
  )
