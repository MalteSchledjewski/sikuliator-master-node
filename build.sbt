name := """sikuliator-master-node"""

version := "0.0.1-SNAPHSOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"



libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",//"9.4.1207",
  "com.typesafe.play" %% "play-slick" % "1.1.1",
  "com.zaxxer" % "HikariCP" % "2.4.1",
  "com.typesafe.slick" %% "slick" % "3.1.1"//,
  //"org.slf4j" % "slf4j-nop" % "1.6.4"
)
libraryDependencies += "com.github.tminglei" %% "slick-pg" % "0.11.0"
//resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


//libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.1.1"
//slick <<= slickCodeGenTask
//
//sourceGenerators in Compile <+= slickCodeGenTask
//
//lazy val slick = TaskKey[Seq[File]]("gen-tables")
//lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
//  val outputDir = dir.getPath
//  val username = "postgres"
//  val password = "Sikuliator"
//  val url = "jdbc:postgresql://localhost:5432/Sikuliator"
//  val jdbcDriver = "org.postgresql.Driver"
//  val slickDriver = "slick.driver.PostgresDriver"
//  val pkg = "models"
//  toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Seq(slickDriver, jdbcDriver, url, outputDir, pkg, username, password), s.log))
//  val fname = outputDir  + "/Tables.scala"
//  Seq(file(fname))
//}

//fork in run := true
