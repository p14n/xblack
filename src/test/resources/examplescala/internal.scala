package internal

/*
 * Contains any element
 *
 */
case class Container(objectType: String,
  containedObject: Array[Byte])

/*
 * A message sent from the api to the back office
 */
case class ApiMessage(
  Body: Option[Container] = None,
  method: Option[String] = None,
  path: Option[String] = None,
  requestId: Option[String] = None,
  person: Option[String] = None,
  status: Option[Int] = None)

