package com.p14n.xblack

import scala.tools.nsc.doc.Universe
import scala.tools.nsc.doc.model.DocTemplateEntity

class Interpreter {

  def interpret(source: Universe): String = {
    val sb = new StringBuffer()
    println("Interpret Universe");
    source.rootPackage.templates.foreach { template =>
      println("template " + template.name)
      if(template.isClass){
        println("Class " + template.name)
        interpretClass(template.asInstanceOf[DocTemplateEntity])
      } else if(template.isPackage){
        println("Package " + template.name)
        interpretPackage(template.asInstanceOf[DocTemplateEntity])
      }
    }
    null
  }
  def interpretClass(cls: DocTemplateEntity): String = {
    null
  }
  def interpretPackage(pkg: DocTemplateEntity): String = {
    pkg.templates.foreach { template =>
      println("template 2 " + template.name)
    }
    null
  }


}
