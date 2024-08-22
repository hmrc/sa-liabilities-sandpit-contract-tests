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

class ServiceHelper extends BaseHelper with HttpClient {
  private val host          = TestConfiguration.url("sa-sandpit")
  private val token         = "Bearer test"
  val toggleStatus: Boolean = sys.props.getOrElse("bearerToken", "false").toBoolean

  val headers: Seq[(String, String)] = if (toggleStatus) {
    Seq(
      "Authorization" -> token,
      "Content-Type"  -> "application/json"
    )
  } else {
    Seq(
      "Content-Type" -> "application/json"
    )
  }

  def getSALiabilitiesSandpit(nino: String): StandaloneWSResponse =
    getUrl(
      s"$host/$nino",
      Some(headers)
    )
}