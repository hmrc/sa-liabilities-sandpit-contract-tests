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

import org.scalatest.Assertions.fail
import play.api.libs.ws.StandaloneWSResponse
import uk.gov.hmrc.api.client.HttpClient
import uk.gov.hmrc.api.conf.TestConfiguration
import uk.gov.hmrc.api.service.AuthService

import play.api.libs.ws.ahc.StandaloneAhcWSClient
import scala.util.Try

class ServiceHelper extends BaseHelper with HttpClient {

  val client: StandaloneAhcWSClient = StandaloneAhcWSClient()
  val authService                   = new AuthService()

  private val host  = TestConfiguration.url("sa-sandpit")
  private val token =
    "Bearer BXQ3/Treo4kQCZvVcCqKPi0hN8uG3okqE4EiOStDBK4KfQ50Ih0hvyIfNvMchL45R4uzM/LSQuMM65UM9ZR4A3sWzltSmVXChmU4R438I+AtJVH76lL2S2sVKP0Th7hf8uj2rQV0egoJC4YmekIWzgw8fJiDl/LDFRp7QnchbRb9KwIkeIPK/mMlBESjue4V,GNAP dummy-c46c6da2e2274c64ab821abda039a6ed"

  val bearerToken: String = new AuthService()
    .postLogin()
    .headers
    .get("Authorization")
    .flatMap(_.headOption)
    .getOrElse(fail("Couldn't retrieve Auth Token"))

  println(bearerToken)

  val headers: Seq[(String, String)] =
    Seq(
      "Authorization" -> bearerToken,
      "Accept"        -> "application/vnd.hmrc.1.0+json"
    )

  def getSALiabilitiesSandpit(nino: String): StandaloneWSResponse =
    getUrl(
      s"$host/$nino",
      Some(headers)
    )
}
