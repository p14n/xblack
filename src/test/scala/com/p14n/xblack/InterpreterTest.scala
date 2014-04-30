package com.p14n.xblack

import org.scalatest.FunSuite
import scala.tools.nsc.io

class DateRangeTest extends FunSuite {

  val parser = new Parser()
  val interpreter = new Interpreter()

  test("Should parse DateRange"){

    val source = io.File("src/test/resources/examplescala/DateRange.scala").slurp()
    val universe = parser.parse(source)

    val dr1 = interpreter.interpret(universe.get)(0)
    val dr2 = ClassDef(name = "DateRange",comment="A date range xmlschema(root)",packageName= "example",params =
      List(ParamVal(name="from",optional=false,typeName=Some("javax.xml.datatype.XMLGregorianCalendar"),comment="From date xmlschema(date)"),
        ParamVal(name="to",optional=true,typeName=Some("javax.xml.datatype.XMLGregorianCalendar"),comment="To date xmlschema(date)")))


    assert(dr1 == dr2)

  }

}
