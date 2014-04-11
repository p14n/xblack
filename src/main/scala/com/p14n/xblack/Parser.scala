package com.p14n.xblack

import scala.tools.nsc.ScalaDoc
import scala.tools.nsc.doc.Settings
import scala.tools.nsc.doc.{ DocFactory, Universe }
import scala.tools.nsc.util.CommandLineParser
import scala.tools.nsc.reporters.ConsoleReporter

class Parser {

  def factory = newDocFactory

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


}
