package com.p14n.xblack

import scala.tools.nsc.ScalaDoc
import scala.tools.nsc.doc.Settings
import scala.tools.nsc.doc.{ DocFactory, Universe }
import scala.tools.nsc.util.CommandLineParser
import scala.tools.nsc.reporters.ConsoleReporter
import scala.tools.nsc.io

import sbt._

object Parser extends Plugin {

  val xsd = taskKey[Unit]("Generate xml schema for case classes.")
  val packageRoot = settingKey[String]("The part of the package that should be removed from namespaces.")
  val schemaRoot = settingKey[String]("The start of the namespace http://...")
  val directories = settingKey[List[String]]("Directories to examine for case classes")

  val newSettings = Seq(
    packageRoot := "",
    schemaRoot := "http://",
    directories := List()
  )

  xsd := {
    genSchemas(directories.value,schemaRoot.value,packageRoot.value)
  }

  def factory = newDocFactory
  def interpreter = new Interpreter()

  def newDocFactory: DocFactory = {
    val settings = new Settings(_ => ())
    settings.scaladocQuietRun = true
    val args = "-usejavacp"
    new ScalaDoc.Command((CommandLineParser tokenize (args)), settings) // side-effecting, I think
    val docFact = new DocFactory(new ConsoleReporter(settings), settings)
    docFact
  }

  def parse(content: String): Option[Universe] = {
    factory.makeUniverse(Right(content))
  }

  def genSchemas(dirs:List[String],schemaRoot:String,packageRoot:String): Unit = {

    val p: List[ClassDef] = dirs.map { arg =>

      val dir = new java.io.File(arg)
      if (dir.exists && dir.isDirectory) {

        dir.listFiles.map { file =>
          if (!file.isDirectory) {
            val universe = parse(io.File(file.getAbsolutePath).slurp()).get
            interpreter.interpret(universe)
            //List[ClassDef]()
          } else {
            List[ClassDef]()
          }
        } flatten
      } else {
        Array[ClassDef]() //Cannot be a list, why?
      }
    } flatten

    val mapCD = p groupBy { _.packageName }

    val s = new Serializer(schemaRoot,packageRoot)

    mapCD.map {
      case (k, v) =>
        println("************************")
        println(k)
        println(s.serialise(v))
    }

  }

}
