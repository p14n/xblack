package com.p14n.xblack

import scala.tools.nsc.doc.Universe
import scala.tools.nsc.doc.model.{DocTemplateEntity,TypeEntity}
import scala.tools.nsc.doc.base.Tooltip

case class ParamVal(name:String,optional:Boolean = false,typeName:Option[String] = None)

class Interpreter {

  def interpret(source: Universe): String = {
    val sb = new StringBuffer()
    println("Interpret Universe");
    source.rootPackage.templates.foreach { template =>
      interpret(template.asInstanceOf[DocTemplateEntity])
    }
    null
  }

  def interpret(template: DocTemplateEntity): String = {
      println("Template " + template.name)
      if(template.isClass){
        return interpretClass(template)
      } else if(template.isPackage){
        return interpretPackage(template)
      }
      println(" - unknown")
      null
  }
  def interpretClass(cls: DocTemplateEntity): String = {
    println("Class " + cls.name)
    /*cls.templates.foreach { template =>
      interpret(template.asInstanceOf[DocTemplateEntity])      
    }*/
    cls.valueParams.foreach { params =>
      params.foreach { param =>
        println(interpretParamType(ParamVal(name=param.name),param.resultType))
      }
    }
    null
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
  def interpretPackage(pkg: DocTemplateEntity): String = {
    println("Package " + pkg.name)
    pkg.templates.foreach { template =>
      interpret(template.asInstanceOf[DocTemplateEntity])
    }
    null
  }


}
