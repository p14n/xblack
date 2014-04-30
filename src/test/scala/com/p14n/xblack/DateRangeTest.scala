package com.p14n.xblack

import org.scalatest.FunSuite
import scala.tools.nsc.io

class DateRangeTest extends FunSuite {

  test("Should parse DateRange"){

    val source = io.File("src/test/resources/examplescala/DateRange.scala").slurp()
    val parser = new Parser()

    val universe = parser.parse(source)

    val interpreter = new Interpreter()

    val dr1 = interpreter.interpret(universe.get)
    val dr2 = List(ClassDef(name = "DateRange",comment="A date range xmlschema(root)",packageName= "example",params =
      List(ParamVal(name="from",optional=false,typeName=Some("javax.xml.datatype.XMLGregorianCalendar"),comment="From date xmlschema(date)"),
        ParamVal(name="to",optional=true,typeName=Some("javax.xml.datatype.XMLGregorianCalendar"),comment="To date xmlschema(date)"))))

    assert(dr1 == dr2)

  }

}
