import sbt.Keys.{libraryDependencies, scalaVersion, version}

lazy val root = (project in file(".")).
  settings(
    name := "CSE511",
    version := "0.1.0",
    scalaVersion := "2.12.15",
    organization  := "org.datasyslab",
    publishMavenStyle := true,
    mainClass := Some("cse511.Main")
  )

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.3.2" % "provided",
  "org.apache.spark" %% "spark-sql" % "3.3.2" % "provided",
)
