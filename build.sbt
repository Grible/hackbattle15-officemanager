name := "Naggy"

version := "0.2.0"

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
  "org.scaldi" %% "scaldi-play" % "0.5.4"
)

herokuAppName in Compile := "naggy"

fork in run := true