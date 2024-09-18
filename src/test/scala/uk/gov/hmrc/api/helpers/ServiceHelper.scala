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

import play.api.libs.ws.StandaloneWSResponse
import uk.gov.hmrc.api.client.HttpClient
import uk.gov.hmrc.api.conf.TestConfiguration
import uk.gov.hmrc.api.service.AuthService

import play.api.libs.ws.ahc.StandaloneAhcWSClient

class ServiceHelper extends BaseHelper with HttpClient {

  val client: StandaloneAhcWSClient = StandaloneAhcWSClient()
  val authService                   = new AuthService()

  private val host = TestConfiguration.url("sa-sandpit")

  def getSALiabilitiesSandpit(nino: String, bearerToken: String): StandaloneWSResponse =
    getUrl(
      s"$host/$nino",
      Some(
        Seq(
          "Authorization" -> bearerToken,
          "Accept"        -> "application/vnd.hmrc.1.0+json"
        )
      )
    )
}
