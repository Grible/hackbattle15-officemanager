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
  "com.github.Shinsuke-Abe" %% "dropbox4s" % "0.2.0",
  "org.yaml" % "snakeyaml" % "1.13"
)

herokuAppName in Compile := "naggy"

fork in run := true