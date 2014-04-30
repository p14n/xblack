package com.p14n.xblack

import scala.xml.Elem

class Serializer {
  val printer = new scala.xml.PrettyPrinter(80,2)

  def serialize(classes: List[ClassDef]): String = {
    val classContent = classes.foldLeft(List[Elem]()){ (s,classs) => serialize(classs) :: s }.reverse
    val packageName = "http://schemas.jhc.co.uk/domain/" + classes(0).packageName

    printer.format(<xs:schema xmlns={packageName}
     xmlns:xs="http://www.w3.org/2001/XMLSchema"
     targetNamespace={packageName}
     elementFormDefault="qualified"
     attributeFormDefault="unqualified">

    {classContent}

    </xs:schema>)


  }
  def serialize(classs: ClassDef): Elem = {
    val name = classs.name
    val comment = classs.comment
    val annotations = classs.params.foldLeft(List[Elem]()){ (s,param) => serialize(param) :: s }.reverse

      <xs:complexType name={name}>
        <xs:annotation>
            <xs:documentation>{comment}</xs:documentation>
        </xs:annotation>
        {annotations}
        </xs:complexType>
  }
  def serialize(param: ParamVal): Elem = {
    val name = param.name
    val meta = getMetadata(param.comment)
    val typeName = translateType(param.typeName,meta)
    val optionality = if(param.optional) "optional" else "required"
    val comment = removeMetadata(param.comment)
       <xs:attribute name={name} type={typeName} use={optionality}>
          <xs:annotation>
            <xs:documentation>{comment}</xs:documentation>
          </xs:annotation>
        </xs:attribute>
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
  def translateType(typeName: Option[String],meta: Set[String]): String = {
    if(meta.contains("date")) return "xs:date"
    typeName match {
      case Some("joda.date.DateTime") => return "xs:dateTime"
      case _ => ""
    }
  }
}
