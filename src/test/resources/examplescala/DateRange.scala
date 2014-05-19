package example;

import org.joda.time.LocalDate

/** 
  * A date range xmlschema(root)
  *
  * @param from From date xmlschema(date)
  * @param to To date xmlschema(date)
  * 
*/
case class DateRange(
 from: LocalDate,
 to: Option[javax.xml.datatype.XMLGregorianCalendar] = None)
