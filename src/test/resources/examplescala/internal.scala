package internal

/**
 * Contains any element
 * 
 * @param objectType The classname of the payload
 * @param containedObject The serialised object
 */
case class Container(objectType: String,
  containedObject: Array[Byte])

/**
 * A message sent from the api to the back office
 * 
 * @param Body The message payload
 * @param method The method (corresponding to HTTP methods like GET) to use
 * @param path The path to target - corresponds to actors
 * @param requestId Identifies the message for the lifetime of this request through the system
 * @param person The uecode of the person instigating this request
 * @param status The status (corresponding to HTTP status codes like 404 Not found) to use
 */
case class ApiMessage(
  Body: Option[Container] = None,
  method: Option[String] = None,
  path: Option[String] = None,
  requestId: Option[String] = None,
  person: Option[String] = None,
  status: Option[Int] = None,
  tags: List[String] = List())
