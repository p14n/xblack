package com.p14n.xblack

import scala.xml.Elem

class Serializer {
  val printer = new scala.xml.PrettyPrinter(80,2)
  val schemaRoot = "http://schemas.jhc.co.uk/domain/"
  val packageRoot = ""

  val known = Map(
    "joda.date.DateTime" -> "xs:dateTime",
    "scala.Int" -> "xs:int",
    "scala.Byte" -> "xs:base64Binary",
    "scala.Predef.String" -> "xs:string")


  def serialise(classes: List[ClassDef]): String = {
    val allNames = classes.foldLeft(Set[String]()){ (s,v) => s + (v.packageName + "." + v.name) }
    val classContent = classes.foldLeft(List[Elem]()){ (s,classs) => serialise(classs,allNames) :: s }.reverse
    val packageName = schemaRoot + classes(0).packageName
    printer.format(

    <xs:schema xmlns={packageName}
     xmlns:xs="http://www.w3.org/2001/XMLSchema"
     targetNamespace={packageName}
     elementFormDefault="qualified"
     attributeFormDefault="unqualified">

    {classContent}

    </xs:schema>)


  }
  def serialise(classs: ClassDef,allNames: Set[String]): Elem = {
    val name = classs.name
    val comment = classs.comment
    val attributes = classs.params.filter {
      p => known.contains(p.typeName.getOrElse(""))
    }.foldLeft(List[Elem]()){ (s,param) => serialiseAttribute(param,allNames) :: s }.reverse
    val elements = serialiseElements(classs.params.filter {
      p => !known.contains(p.typeName.getOrElse(""))
    } , allNames)

      <xs:complexType name={name}>
        <xs:annotation>
            <xs:documentation>{comment}</xs:documentation>
        </xs:annotation>
        {elements}
        {attributes}
      </xs:complexType>
  }
  def serialiseElements(params: List[ParamVal],allNames: Set[String]): List[Elem] = {
    if(params.size == 0) List[Elem]() else {
  	  List(<xs:sequence>
        {params.foldLeft(List[Elem]()){ (s,param) => serialiseElement(param,allNames) :: s }.reverse}
   		</xs:sequence>)
    }
  }
  def serialiseAttribute(param: ParamVal,allNames: Set[String]): Elem = {
    val name = param.name
    val meta = getMetadata(param.comment)
    val typeName = translateType(param.typeName,meta,allNames)
    val optionality = if(param.optional) "optional" else "required"
    val comment = removeMetadata(param.comment)
       <xs:attribute name={name} type={typeName} use={optionality}>
          <xs:annotation>
            <xs:documentation>{comment}</xs:documentation>
          </xs:annotation>
        </xs:attribute>
  }
  def serialiseElement(param: ParamVal,allNames: Set[String]): Elem = {
    val name = param.name
    val meta = getMetadata(param.comment)
    val typeName = translateType(param.typeName,meta,allNames)
    val optionality = if(param.optional) "optional" else "required"
    val comment = removeMetadata(param.comment)
       <xs:element name={name} type={typeName} minoccurs={if(param.optional) "0" else "1"} maxoccurs={if(param.many) "unbounded" else "1"}>
          <xs:annotation>
            <xs:documentation>{comment}</xs:documentation>
          </xs:annotation>
        </xs:element>
  }
  def removeMetadata(comment:String): String = {
    val index = comment.indexOf("xmlschema(")
    if(index > -1) comment.substring(0,index-1) else comment
  }
  def getMetadata(comment:String): Set[String] = {
    val index = comment.indexOf("xmlschema(")
    
    if(index > -1) {
      val endIndex = comment.indexOf(")",index)
      return comment.substring(index + 10,endIndex).split(",").foldLeft(Set[String]()){
        (s,tag) => s + tag
      }

    }
    Set[String]()
  }
  def translateType(typeName: Option[String],meta: Set[String],allNames: Set[String]): String = {
    if(meta.contains("date")) return "xs:date"
    typeName match {
      case Some(v) => if(known.contains(v)) known(v) else {
        val point = v.lastIndexOf(".")
        println("translate type "+ v)
        println("from "+ allNames)
        if(allNames.contains(v)) {
          if(point > -1) v.substring(point + 1) else v
        } else {
          if(v.startsWith(packageRoot)){
            v.substring(packageRoot.length,point).replace(".","-") + ":" + v.substring(point + 1)
          } else {
            v.substring(0,point).replace(".","-") + ":" + v.substring(point + 1)
          }
        }
      }
      case _ => ""
    }
  }
}
