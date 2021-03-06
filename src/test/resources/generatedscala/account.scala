// Generated by <a href="http://scalaxb.org/">scalaxb</a>.



case class Account(Product: ProductType,
  DefaultBankAccounts: Option[DefaultBankAccounts] = None,
  Balances: Option[Balances] = None,
  Links: Option[Links] = None,
  code: Option[String] = None,
  name: Option[String] = None,
  typeValue: Option[String] = None,
  cserviceLevel: Option[String] = None,
  dealingStatus: Option[Int] = None,
  cbaseCcy: Option[String] = None,
  csettCcy: Option[String] = None,
  cvalCcy: Option[String] = None)


case class Accounts(Account: Account*)


case class AccountSummary(Accounts: Accounts,
  AvailableToWithdraw: CashValue,
  HoldingValuation: CashValue)


case class Portfolio(ValuationFormat: ValuationFormat,
  InvestmentObjective: InvestmentObjective,
  Description: String,
  Accounts: Accounts,
  cvalCcy: Option[String] = None,
  cserviceLevel: Option[String] = None,
  typeValue: Option[String] = None,
  code: Option[String] = None)


case class Portfolios(Portfolio: Portfolio*)


case class DefaultBankAccount(BankAccount: BankAccount,
  defaultType: Option[String] = None)


case class DefaultBankAccounts(DefaultBankAccount: DefaultBankAccount*)


case class AccountRelationship(Account: Account,
  croleId: Option[String] = None)


case class AccountRelationships(AccountRelationship: AccountRelationship*)


case class BankAccount(Balances: Balances,
  country: Option[String] = None,
  iban: Option[String] = None,
  sort: Option[String] = None,
  ukAccount: Option[String] = None,
  roll: Option[String] = None)


case class Balances(AvailableToWithdraw: CashValue,
  AvailableToInvest: CashValue,
  HoldingValuation: CashValue)


case class AccountLinkEvent(id: Option[String] = None,
  oldUecode: Option[String] = None,
  newUecode: Option[String] = None,
  accountNumber: Option[String] = None,
  username: Option[String] = None,
  changeType: Option[String] = None,
  performed: Option[javax.xml.datatype.XMLGregorianCalendar] = None,
  ownershipConfirmed: Option[Boolean] = None,
  authorisationChecked: Option[Boolean] = None,
  warningsOverridden: Option[String] = None)


case class AccountLinkEvents(AccountLinkEvent: AccountLinkEvent*)

