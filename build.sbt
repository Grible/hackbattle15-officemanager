name := """play-scala"""

version := "0.0.1"

resolvers += "bintray" at "http://dl.bintray.com/shinsuke-abe/maven"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  filters,
  "org.scalaj" %% "scalaj-http" % "1.0.1",
  "com.github.Shinsuke-Abe" %% "dropbox4s" % "0.2.0"
)

herokuAppName in Compile := "naggy"

fork in run := true