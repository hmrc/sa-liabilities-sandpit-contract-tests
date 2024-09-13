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

import uk.gov.hmrc.api.client.HttpClient
import scala.util.Random
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global

object TestDataGenerator extends HttpClient {

  val random = new Random()

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

  def createBalanceDetails(nino: String): Future[Unit] = {
    val url = s"http://localhost:9900/balance/$nino"

    Future {
      val response = postUrl(
        url,
        "",
        Some(
          Seq(
            "Accept"                    -> "application/vnd.hmrc.1.0+json",
            "X-USE-STRATEGY-GENERATION" -> "faker"
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
