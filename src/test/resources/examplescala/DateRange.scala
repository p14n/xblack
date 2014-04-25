package example;

/** 
  *  A date range
  * 
  *  @param from From date
  *  @param to To date
  * 
*/
case class DateRange(
 from: javax.xml.datatype.XMLGregorianCalendar,
 to: Option[javax.xml.datatype.XMLGregorianCalendar] = None)
