package com.p14n.xblack

import scala.tools.nsc.doc.Universe
import scala.tools.nsc.doc.model.{ DocTemplateEntity, TypeEntity, Val }
import scala.tools.nsc.doc.base.{Tooltip, LinkToTpl}
import scala.tools.nsc.doc.base.comment.{ Comment, Paragraph, Chain, Summary, Text }

case class ParamVal(name: String, comment: String, optional: Boolean = false, typeName: Option[String] = None, many: Boolean = false)
case class ClassDef(packageName: String, name: String, params: List[ParamVal], comment: String)

class Interpreter {

  def interpret(source: Universe): List[ClassDef] = {
    val sb = new StringBuffer()
    source.rootPackage.templates.map(template =>
      interpret(template.asInstanceOf[DocTemplateEntity])).flatten
  }

  def interpret(template: DocTemplateEntity, packageName: String = ""): List[ClassDef] = {
    println("Template " + template.name)
    if (template.isClass) {
      return List(interpretClass(packageName, template))
    } else if (template.isPackage) {
      return interpretPackage(template)
    }
    println(" - unknown")
    List()
  }

  def interpretClass(packageName: String, cls: DocTemplateEntity): ClassDef = {
    println("Class " + cls.name)
    val allParamNameMap = cls.values.foldLeft(Map[String,Val]()){ (m,v) => m + (v.name -> v) }

    val caseParams = cls.valueParams.foldLeft(List[ParamVal]()) { (l, v) =>
      v.foldLeft(l) { (li, vi) => 
        val param = allParamNameMap(vi.name)
        interpretParamType(ParamVal(name = param.name, comment = interpretComment(param.comment)), param.resultType) :: li
      }
    }.reverse


    ClassDef(packageName = packageName, comment = interpretComment(cls.comment), name = cls.name, params = caseParams)
  }

  def interpretComment(maybe: Option[Comment]): String = {
    {
      for (comment <- maybe) yield {
        comment.body.blocks.filter { case pa: Paragraph => true case _ => false }.foldLeft("") { (content, p) =>
          p.asInstanceOf[Paragraph].text match {
            case c: Chain =>
              c.items.filter { case s: Summary => true case _ => false }.foldLeft("") { (content2, value) =>
                value.asInstanceOf[Summary].text match {
                  case t: Text => content2 + t.text.trim
                  case _ => content2
                }
              }
            case _ => content
          }
        }
      }
    } getOrElse { "" }
  }

  def interpretParamType(paramVal: ParamVal, paramType: TypeEntity): ParamVal = {
    println("Type " + paramType)
    paramType.refEntity.values.foldLeft(paramVal) { (content, value) =>
      println("Value " + value)
      value match {
        case (Tooltip("scala.Option"), y) => content.copy(optional = true);
        case (Tooltip("scala.List"), y) => content.copy(many = true);          
        case (Tooltip(x), y) => content.copy(typeName = Some(x));
        case (LinkToTpl(x), y) => content.copy(typeName = Some(x.toString()));
        case _ => content;
      }
    }
  }

  def interpretPackage(pkg: DocTemplateEntity): List[ClassDef] = {
    println("Package " + pkg.name)
    pkg.templates.map(template =>
      interpret(template.asInstanceOf[DocTemplateEntity], pkg.name)).flatten
  }
}
