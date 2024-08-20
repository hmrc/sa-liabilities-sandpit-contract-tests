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
  def checkNINOFormat(nino: String): Unit = {
    val expectedRegex = """^[A-Z]{2}[0-9]{6}[A-Z]{0,1}$""".r
    val isMatch       = expectedRegex.matches(nino)
    assert(isMatch)
  }

  def checkResponseStatus(status: Int, expected: Int): Unit =
    assert(status == expected, message = s"Expected a Status of $expected : Actual Status is $status")

  def checkSALiabilitiesResponse(response: String, expectedTypes: Map[String, Any]): Unit = {
    val responseBody: JsValue    = Json.parse(response)
    val balances: List[JsObject] = (responseBody \ "balanceDetails").as[List[JsObject]] // Update to balanceDetails
    balances.foreach { balanceItem =>
      expectedTypes.foreach { case (key, expectedType) =>
        val value      = (balanceItem \ key).get
        val actualType = value match {
          case _: JsString => "string"
          case _: JsNumber => "number"
          case _           => "unknown"
        }
        assert(actualType == expectedType, s"Expected type is $expectedType, but got $actualType.")
      }
    }
  }
}
