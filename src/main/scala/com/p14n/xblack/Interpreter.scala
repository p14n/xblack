package com.p14n.xblack

import scala.tools.nsc.doc.Universe
import scala.tools.nsc.doc.model.{DocTemplateEntity,TypeEntity}
import scala.tools.nsc.doc.base.Tooltip

case class ParamVal(name:String,optional:Boolean = false,typeName:Option[String] = None)
case class ClassDef(packageName:String,name:String,params:List[ParamVal])

class Interpreter {

  def interpret(source: Universe): String = {
    val sb = new StringBuffer()
    println("Interpret Universe");
    source.rootPackage.templates.map( template =>
      interpret(template.asInstanceOf[DocTemplateEntity])
    ).flatten.map( clz => println(clz))
    null
  }

  def interpret(template: DocTemplateEntity,packageName:String = ""): List[ClassDef] = {
      println("Template " + template.name)
    println(template.comment)
      if(template.isClass){
        return List(interpretClass(packageName,template))
      } else if(template.isPackage){
        return interpretPackage(template)
      }
      println(" - unknown")
      List()
  }
  def interpretClass(packageName:String,cls: DocTemplateEntity): ClassDef = {
    println("Class " + cls.name)
    cls.values.map( v => println(v.comment) ) //values gives the commented 
    ClassDef(packageName = packageName, name = cls.name,
    cls.valueParams.map( params => params.map( 
      param => interpretParamType(ParamVal(name=param.name),param.resultType))).flatten)

  }

  def interpretParamType(paramVal:ParamVal,paramType: TypeEntity):ParamVal = {
    paramType.refEntity.values.foldLeft(paramVal) { (content, value) =>
      value match {
        case (Tooltip("scala.Option"),y) => content.copy(optional = true);
        case (Tooltip(x),y) => content.copy(typeName=Some(x));
        case _ => content;
      } 
    }
  }
  def interpretPackage(pkg: DocTemplateEntity): List[ClassDef] = {
    println("Package " + pkg.name)
    pkg.templates.map( template =>
      interpret(template.asInstanceOf[DocTemplateEntity],pkg.name)
    ).flatten
  }
}
