import scala.sys.process._

ThisBuild / tlBaseVersion := "0.1" // your current series x.y
ThisBuild / organization := "org.creativescala"
ThisBuild / organizationName := "Creative Scala"
ThisBuild / startYear := Some(2024)
ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("noelwelsh", "Noel Welsh")
)

// true by default, set to false to publish to s01.oss.sonatype.org
ThisBuild / tlSonatypeUseLegacyHost := true

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "org.creativescala"
ThisBuild / homepage := Some(
  url("https://github.com/creativescala/creative-scala-theme")
)

val css = taskKey[Int]("Command to generate css")

lazy val root = (project in file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "creative-scala-theme",
    libraryDependencies += "org.typelevel" %% "laika-io" % "1.0.0",
    css := {
      val cmd =
        s"npx tailwindcss -i ${sourceDirectory.value.toString}/main/css/creative-scala.css -o src/main/resources/creativescala/css/creative-scala.css"
      println(cmd)
      cmd.!
    },
    pluginCrossBuild / sbtVersion := {
      scalaBinaryVersion.value match {
        case "2.12" => "1.2.8" // set minimum sbt version
      }
    }
  )
