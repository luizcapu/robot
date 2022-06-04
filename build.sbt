ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.15"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.1.0" % Test
)

enablePlugins(SiteScaladocPlugin)

siteDirectory := file(".")
SiteScaladoc / siteSubdirName := "docs/scaladocs"

lazy val root = (project in file("."))
  .settings(
    name := "robot"
  )
