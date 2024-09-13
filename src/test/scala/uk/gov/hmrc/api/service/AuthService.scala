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

package uk.gov.hmrc.api.service

import play.api.libs.ws.StandaloneWSResponse
import uk.gov.hmrc.api.client.HttpClient
import uk.gov.hmrc.api.conf.TestConfiguration

class AuthService extends HttpClient {
  val host: String = TestConfiguration.url("auth")

  def authPayload(NINO: String, UTR: String, CREDID: String): String =
    s"""
       |{
       |  "credId": "$CREDID",
       |  "affinityGroup": "Individual",
       |  "confidenceLevel": 50,
       |  "credentialStrength": "strong",
       |  "nino" : "$NINO",
       |  "enrolments": [
       |    {
       |      "key": "IR-SA",
       |      "identifiers": [
       |        {
       |          "key": "UTR",
       |          "value": "$UTR"
       |        }
       |      ],
       |      "state": "Activated"
       |    }
       |  ]
       |}
       """.stripMargin

  def postLogin(nino: String, utr: String, credID: String): StandaloneWSResponse =
    postUrl(
      s"$host/government-gateway/session/login",
      authPayload(nino, utr, credID),
      Some(Seq("Content-Type" -> "application/json"))
    )

}
