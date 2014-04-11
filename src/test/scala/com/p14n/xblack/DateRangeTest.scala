package com.p14n.xblack

import org.scalatest.FunSuite
import scala.tools.nsc.io

class DateRangeTest extends FunSuite {

  test("Should parse DateRange"){

    val source = io.File("src/test/resources/examplescala/DateRange.scala").slurp()
    val parser = new Parser()

    val universe = parser.parse(source)

    val interpreter = new Interpreter()

    interpreter.interpret(universe.get)

  }

}
