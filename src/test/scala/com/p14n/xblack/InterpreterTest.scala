package com.p14n.xblack

import org.scalatest.FunSuite
import scala.tools.nsc.io

class InterpreterTest extends FunSuite {

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
  test("Should parse ApiMessage"){

    val source = io.File("src/test/resources/examplescala/internal.scala").slurp()
    val universe = parser.parse(source)

    val dr1 = interpreter.interpret(universe.get)(0)
    val dr2 = ClassDef(name = "ApiMessage",comment="A message sent from the api to the back office",packageName= "internal",params =
      List(ParamVal(name="Body",optional=true,typeName=Some("internal.Container"),comment="The message payload"),
        ParamVal(name="method",optional=true,typeName=Some("scala.Predef.String"),comment="The method (corresponding to HTTP methods like GET) to use"),
        ParamVal(name="path",optional=true,typeName=Some("scala.Predef.String"),comment="The path to target - corresponds to actors"),
        ParamVal(name="requestId",optional=true,typeName=Some("scala.Predef.String"),comment="Identifies the message for the lifetime of this request through the system"),
        ParamVal(name="person",optional=true,typeName=Some("scala.Predef.String"),comment="The uecode of the person instigating this request"),
        ParamVal(name="status",optional=true,typeName=Some("scala.Int"),comment="The status (corresponding to HTTP status codes like 404 Not found) to use"),
        ParamVal(name="tags",comment="",optional=false,typeName=Some("scala.Predef.String"),many=true),
        ParamVal(name="errors",comment="",optional=true,typeName=Some("internal.Errors"),many=false)))

    assert(dr1 == dr2)

  }
  test("Should parse Errors"){

    val source = io.File("src/test/resources/examplescala/internal.scala").slurp()
    val universe = parser.parse(source)

    val dr1 = interpreter.interpret(universe.get)(1)
    val dr2 = ClassDef(name = "Errors",comment="",packageName= "internal",params =
      List(ParamVal(name="error",comment="",optional=false,typeName=Some("internal.Error"),many=true)))

    assert(dr1 == dr2)

  }

}
