package example;

/** 
  *  A date range xmlschema(root)
  * 
  *  @param from From date xmlschema(date)
  *  @param to To date xmlschema(date)
  * 
*/
case class DateRange(
 from: javax.xml.datatype.XMLGregorianCalendar,
 to: Option[javax.xml.datatype.XMLGregorianCalendar] = None)
