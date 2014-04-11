name := "xblack"

version := "1.0"

scalaVersion := "2.10.2"

libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.1.3"

fork in Test := true