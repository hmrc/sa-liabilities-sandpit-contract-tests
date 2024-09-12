import sbt.*

object Dependencies {

  val test: Seq[ModuleID] = Seq(
    "com.typesafe"         % "config"                  % "1.4.3"  % Test,
    "com.typesafe.play"   %% "play-ahc-ws-standalone"  % "2.2.5"  % Test,
    "com.typesafe.play"   %% "play-ws-standalone-json" % "2.2.5"  % Test,
    "com.vladsch.flexmark" % "flexmark-all"            % "0.64.8" % Test,
    "org.scalatest"       %% "scalatest"               % "3.2.18" % Test,
    "org.slf4j"            % "slf4j-simple"            % "2.0.9"  % Test,
    "io.circe"            %% "circe-core"              % "0.14.7" % Test,
    "io.circe"            %% "circe-generic"           % "0.14.7" % Test,
    "io.circe"            %% "circe-parser"            % "0.14.9" % Test
  )

}
