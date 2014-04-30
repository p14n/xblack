package com.p14n.xblack

import org.scalatest.FunSuite
import scala.tools.nsc.io

class SerializerTest extends FunSuite {

  test("Should parse DateRange"){
    val dr = List(ClassDef(name = "DateRange",comment="A date range",packageName= "example",params =
      List(ParamVal(name="from",optional=false,typeName=Some("joda.date.DateTime"),comment="From date xmlschema(date)"),
        ParamVal(name="to",optional=true,typeName=Some("joda.date.DateTime"),comment="To date"))))

    val s = new Serializer()
    val source = io.File("src/test/resources/exampleschema/daterange.xsd").slurp()
    val serialized = s.serialize(dr)

    assert(serialized === source)
  }
  test("Should parse Internal package"){
    val dr = List(ClassDef(name = "DateRange",comment="A date range",packageName= "example",params =
      List(ParamVal(name="from",optional=false,typeName=Some("joda.date.DateTime"),comment="From date xmlschema(date)"),
        ParamVal(name="to",optional=true,typeName=Some("joda.date.DateTime"),comment="To date"))))

    val s = new Serializer()
    val source = io.File("src/test/resources/exampleschema/internal.xsd").slurp()
    val serialized = s.serialize(dr)

    assert(serialized === source)
  }

}
