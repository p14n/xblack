// Generated by <a href="http://scalaxb.org/">scalaxb</a>.



case class Holding(Instrument: Instrument,
  BookCost: CashValue,
  Value: CashValue,
  quantity: Option[BigDecimal] = None)


case class Holdings(Holding: Holding*)

