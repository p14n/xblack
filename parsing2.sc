  import scala.tools.partest.ScaladocModelTest
  import scala.tools.nsc.doc.model.{ Class, Package}
  import scala.tools.nsc.doc.Settings
  import scala.tools.nsc.util.CommandLineParser
  import scala.tools.nsc.reporters.ConsoleReporter
  import scala.tools.nsc.ScalaDoc
  import scala.tools.nsc.doc.{ DocFactory, Universe }
  import scala.tools.nsc.doc.model.DocTemplateEntity

object parsing2 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  def newDocFactory: DocFactory = {
    val settings = new Settings(_ => ())
    settings.scaladocQuietRun = true // yaay, no more "model contains X documentable templates"!
    val args = "-usejavacp"
    new ScalaDoc.Command((CommandLineParser tokenize (args)), settings) // side-effecting, I think
    val docFact = new DocFactory(new ConsoleReporter(settings), settings)
    docFact
  }                                               //> newDocFactory: => scala.tools.nsc.doc.DocFactory

 def model: Option[Universe] = newDocFactory.makeUniverse(Right("""case class hh(thing: String)"""))
                                                  //> model: => Option[scala.tools.nsc.doc.Universe]
 
 val mem1 = model.get.rootPackage.templates(0)    //> mem1  : scala.tools.nsc.doc.model.TemplateEntity with scala.tools.nsc.doc.mo
                                                  //| del.MemberEntity = hh
 mem1.isInstanceOf[DocTemplateEntity]             //> res0: Boolean = true
 mem1.isClass                                     //> res1: Boolean = true
 
 val c1 = mem1.asInstanceOf[DocTemplateEntity]    //> c1  : scala.tools.nsc.doc.model.DocTemplateEntity = hh
 
 val val1 = c1.valueParams(0)(0)                  //> val1  : scala.tools.nsc.doc.model.ValueParam = scala.tools.nsc.doc.model.Mo
                                                  //| delFactory$$anon$20@34418cee
 val1.name                                        //> res2: String = thing
 val1.resultType                                  //> res3: scala.tools.nsc.doc.model.TypeEntity = String

}