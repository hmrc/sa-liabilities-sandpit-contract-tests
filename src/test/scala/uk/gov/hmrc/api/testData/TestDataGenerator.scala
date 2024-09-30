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

package uk.gov.hmrc.api.testData

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.api.client.HttpClient

import scala.util.Random
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object TestDataGenerator extends HttpClient {

  val random = new Random()

  def generatePutRequestBody(): JsValue = {
    val payableAmount    = random.nextInt(10000) + 1
    val pendingDueAmount = random.nextInt(5000) + 1
    val overdueAmount    = random.nextInt(2000) + 1
    val totalBalance     = payableAmount + pendingDueAmount + overdueAmount

    val pendingDueDate = LocalDate.now().minusDays(random.nextInt(30)).format(DateTimeFormatter.ISO_LOCAL_DATE)
    val payableDueDate = LocalDate.now().minusDays(random.nextInt(30)).format(DateTimeFormatter.ISO_LOCAL_DATE)

    Json.obj(
      "totalBalance"     -> totalBalance,
      "pendingDueDate"   -> pendingDueDate,
      "pendingDueAmount" -> pendingDueAmount,
      "payableAmount"    -> payableAmount,
      "overdueAmount"    -> overdueAmount,
      "payableDueDate"   -> payableDueDate
    )

  }

  def generateNINO(): String = {
    val validPrefixes = Seq("AA", "BB", "CC")
    val prefix        = validPrefixes(random.nextInt(validPrefixes.length))
    val digits        = (1 to 6).map(_ => random.nextInt(10)).mkString
    val validSuffixes = "ABCD"
    val suffix        = validSuffixes(random.nextInt(validSuffixes.length)).toString
    s"$prefix$digits$suffix"
  }

  def generateUTR(): String =
    (1 to 10).map(_ => random.nextInt(10)).mkString

  def generateCredID(): String =
    (1 to 16).map(_ => random.nextInt(10)).mkString

  def createBalanceDetailsAllFields(nino: String): Future[Unit] = {
    val url = s"http://localhost:9900/balance/$nino"

    Future {
      val response = postUrl(
        url,
        "",
        Some(
          Seq(
            "Accept"                    -> "application/json",
            "X-USE-STRATEGY-GENERATION" -> "randomize"
          )
        )
      )
      if (response.status == 201 && response.body.contains("Balance updated successfully")) {
        println(s"Test data created for $nino successfully.")
      } else {
        println(s"Test data creation failed with status: ${response.status}, Body: ${response.body}")
      }
    }

  }

  def createBalanceDetailsSelectedFields(nino: String, reqPayload: JsValue): Future[Unit] = {
    val url                          = s"http://localhost:9900/balance/$nino"
    val requestPayloadString: String = reqPayload.toString
    Future {
      val response = putUrl(
        url,
        requestPayloadString,
        Some(
          Seq(
            "Content-Type" -> "application/json",
            "Accept"       -> "application/json"
          )
        )
      )
      if (response.status == 201 && response.body.contains("Balance updated successfully")) {
        println(s"Test data created for $nino successfully.")
      } else {
        println(s"Test data creation failed with status: ${response.status}, Body: ${response.body}")
      }
    }

  }
}
