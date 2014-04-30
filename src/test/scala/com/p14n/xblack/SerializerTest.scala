package com.p14n.xblack

import org.scalatest.FunSuite
import scala.tools.nsc.io

class SerializerTest extends FunSuite {

  test("Should parse DateRange"){
    val dr = List(ClassDef(name = "DateRange",comment="A date range",packageName= "example",params =
      List(ParamVal(name="from",optional=false,typeName=Some("joda.date.DateTime"),comment="From date"),
        ParamVal(name="to",optional=true,typeName=Some("joda.date.DateTime"),comment="To date"))))

    val s = new Serializer()
    val source = io.File("src/test/resources/examplescala/DateRange.scala").slurp()
    assert(s.serialize(dr) === source)
  }

}
