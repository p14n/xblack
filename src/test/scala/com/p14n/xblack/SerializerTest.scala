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
    val serialized = s.serialise(dr)

    assert(serialized === source)
  }
  test("Should parse Combined"){
    val dr = List(ClassDef(name = "DateMessage",comment="",packageName= "combined",params =
      List(ParamVal(name="msg",optional=false,typeName=Some("internal.ApiMessage"),comment=""),
        ParamVal(name="dates",optional=false,typeName=Some("example.DateRange"),comment=""))))

    val s = new Serializer()
    val source = io.File("src/test/resources/exampleschema/combined.xsd").slurp()
    val serialized = s.serialise(dr)

    assert(serialized === source)
  }
  test("Should parse Internal package"){

    val dr = List(
      ClassDef(packageName = "internal",name="Container",params =
        List(
          ParamVal(name = "objectType",optional=false,typeName=Some("scala.Predef.String"),comment="The classname of the payload"),
          ParamVal(name = "containedObject",optional=false,typeName=Some("scala.Byte"),comment="The serialised object")            
        ),comment="Contains any element"
      ),

      ClassDef(packageName = "internal",name="ApiMessage",params =
      List(
        ParamVal(name="Body",comment="The message payload",optional = true,typeName=Some("internal.Container")),
        ParamVal(name="method",comment="The method (corresponding to HTTP methods like GET) to use",optional=true,typeName=Some("scala.Predef.String")),
        ParamVal(name="path",comment="The path to target - corresponds to actors",optional=true,typeName=Some("scala.Predef.String")),
        ParamVal(name="requestId",comment="Identifies the message for the lifetime of this request through the system",optional=true,typeName=Some("scala.Predef.String")),
        ParamVal(name="person",comment="The uecode of the person instigating this request",optional=true,typeName=Some("scala.Predef.String")),
        ParamVal(name="status",comment="The status (corresponding to HTTP status codes like 404 Not found) to use",optional=true,typeName=Some("scala.Int")),
        ParamVal(name="tags",comment="",optional=false,typeName=Some("scala.Predef.String"),many=true),
        ParamVal(name="errors",comment="",optional=true,typeName=Some("internal.Errors"),many=false)),
        comment="A message sent from the api to the back office"))

    val s = new Serializer()
    val source = io.File("src/test/resources/exampleschema/internal.xsd").slurp()
    val serialized = s.serialise(dr)

    assert(serialized === source)
  }

}
