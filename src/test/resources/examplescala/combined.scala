package combined

import example.DateRange
import internal.ApiMessage

case class DateMessage(msg: ApiMessage, dates: DateRange)
