// Generated by <a href="http://scalaxb.org/">scalaxb</a>.



case class TransactionType(transactionCode: Option[String] = None)


case class Transaction(TransactionType: TransactionType,
  TransactionSourceId: TransactionSourceId,
  InstrumentHeading: InstrumentHeading,
  transactionDate: Option[javax.xml.datatype.XMLGregorianCalendar] = None,
  reference: Option[String] = None,
  quantity: Option[BigDecimal] = None,
  valueAttribute: Option[BigDecimal] = None)


case class ContractId(broker: Option[String] = None,
  contractReference: Option[String] = None,
  year: Option[String] = None)


case class TransactionSourceId(ContractId: ContractId,
  CAEventID: CAEventID)


case class Transactions(Transaction: Transaction*)

