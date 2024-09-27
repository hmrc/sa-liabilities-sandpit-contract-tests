/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.api.helpers

import play.api.libs.json._

trait BaseHelper {
  val expectedTypes: Map[String, String] = Map(
    "totalBalance"     -> "number",
    "pendingDueDate"   -> "string",
    "pendingDueAmount" -> "number",
    "payableAmount"    -> "number",
    "payableDueDate"   -> "string",
    "overdueAmount"    -> "number"
  )

  val mandatoryFields: Set[String] = Set("pendingDueAmount", "payableAmount", "overdueAmount")

  def checkBalanceDetailsArrayLength(balanceDetails: JsValue,  count: Int): Unit =

    val balanceDetailsArray = (balanceDetails \ "balanceDetails").as[Seq[JsObject]]
    assert(
      balanceDetailsArray.length == count,
      s"Test failed: Found ${balanceDetailsArray.length} liabilities, but expected $count liabilities"
    )
  def isValidNumberFormat(value: BigDecimal): Boolean                                         =
    value >= BigDecimal("-99999999999.99") && value <= BigDecimal("99999999999.99") && value.scale <= 3

  def isValidStringFormat(value: String): Boolean = {
    val dateRegex = """\d{4}-\d{2}-\d{2}""".r
    dateRegex.matches(value)
  }

  def checkNINOFormat(nino: String): Unit = {
    val expectedRegex = """^[A-Z]{2}[0-9]{6}[A-Z]{0,1}$""".r
    val isMatch       = expectedRegex.matches(nino)
    assert(isMatch)
  }

  def checkResponseStatus(status: Int, expected: Int): Unit =
    assert(status == expected, message = s"Expected a Status of $expected : Actual Status is $status")

  def checkSALiabilitiesResponse(response: String, mandatoryOnly: Boolean = false): Unit = {
    val responseBody: JsValue    = Json.parse(response)
    val balances: List[JsObject] = (responseBody \ "balanceDetails").as[List[JsObject]]

    balances.foreach { balanceItem =>
      expectedTypes.foreach { case (key, expectedType) =>
        val valueOpt = (balanceItem \ key).asOpt[JsValue]

        if (!mandatoryOnly || mandatoryFields.contains(key)) {
          valueOpt match {
            case Some(value) =>
              val actualType = value match {
                case _: JsString => "string"
                case _: JsNumber => "number"
                case _           => "unknown"
              }

              assert(actualType == expectedType, s"Expected type is $expectedType, but got $actualType.")

              actualType match {
                case "number" =>
                  val amount = value.as[BigDecimal]
                  assert(isValidNumberFormat(amount), s"Invalid format for $key: $amount")

                case "string" =>
                  val date = value.as[String]
                  assert(isValidStringFormat(date), s"Invalid format for $key: $date")
              }
            case None        =>
              if (mandatoryFields.contains(key)) {
                throw new AssertionError(s"Missing mandatory field: $key")
              }
          }
        }
      }
    }
  }
}
