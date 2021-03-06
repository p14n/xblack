// Generated by <a href="http://scalaxb.org/">scalaxb</a>.



case class MiddleNames(Line: String*)

trait MaritalStatus

object MaritalStatus {
  def fromString(value: String, scope: scala.xml.NamespaceBinding): MaritalStatus = value match {
    case "single" => Single
    case "married" => Married
    case "divorced" => Divorced

  }
}

case object Single extends MaritalStatus { override def toString = "single" }
case object Married extends MaritalStatus { override def toString = "married" }
case object Divorced extends MaritalStatus { override def toString = "divorced" }


case class Individual(EmailAddresses: Option[EmailAddresses] = None,
  Title: Option[String] = None,
  MiddleNames: Option[MiddleNames] = None,
  Dob: Option[javax.xml.datatype.XMLGregorianCalendar] = None,
  Dod: Option[javax.xml.datatype.XMLGregorianCalendar] = None,
  Retired: Option[javax.xml.datatype.XMLGregorianCalendar] = None,
  Residence: Option[Country] = None,
  Nationality: Option[Country] = None,
  Occupation: Option[String] = None,
  Language: Option[String] = None,
  MaritalStatus: Option[MaritalStatus] = None,
  PhoneNumbers: Option[PhoneNumbers] = None,
  Addresses: Option[Addresses] = None,
  relatedAccounts: Option[AccountRelationships] = None,
  relatedCompanies: Option[CompanyRelationships] = None,
  Links: Option[Links] = None,
  uecode: String,
  forename: Option[String] = None,
  surname: Option[String] = None,
  username: Option[String] = None)


case class Individuals(Individual: Individual*)


case class IndividualRelationship(Individual: Individual,
  croleId: Option[String] = None)


case class IndividualRelationships(IndividualRelationship: IndividualRelationship*)

