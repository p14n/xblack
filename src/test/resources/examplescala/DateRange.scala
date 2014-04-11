package example;

/** A date range
*/
case class DateRange(from: javax.xml.datatype.XMLGregorianCalendar,
  to: Option[javax.xml.datatype.XMLGregorianCalendar] = None)