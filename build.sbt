name := "Scala Playground"

organization := "com.denner"

version := "0.1-SNAPSHOT"

scalaVersion := "2.10.0"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "1.9.1"
)

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/",
  "typesafe repo" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= Seq(
  "io.spray"          % "spray-routing" % "1.1-M7",
  "io.spray"          % "spray-can"     % "1.1-M7",
  "io.spray"          % "spray-httpx"   % "1.1-M7",
  "io.spray"          % "spray-testkit" % "1.1-M7",
  "com.typesafe.akka" %% "akka-actor"   % "2.1.0",
  "io.spray"          %% "spray-json"   % "1.2.3"
)

resolvers ++= Seq(
  "Scala Tools Snapshots" at "http://scala-tools.org/repo-snapshots/"
)

libraryDependencies ++= Seq(
  "org.scalaz"    %% "scalaz-core"               % "6.0.4",
  "org.typelevel" %% "scalaz-contrib-210"        % "0.1",
  "org.typelevel" %% "scalaz-contrib-validation" % "0.1",
  "org.typelevel" %% "scalaz-contrib-undo"       % "0.1",
  "org.typelevel" %% "scalaz-dispatch"           % "0.1"
)

resolvers ++= Seq(
  "shapeless repo" at "http://oss.sonatype.org/content/repositories/releases/"
)

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "1.2.3"
)
