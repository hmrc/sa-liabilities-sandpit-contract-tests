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
import uk.gov.hmrc.api.service.AuthService

import javax.inject.Inject

class AuthHelper @Inject() (authService: AuthService) {

  def getAuthBearerToken(nino: String, utr: String, credID: String): String =
    authService
      .postLogin(nino, utr, credID)
      .header("Authorization")
      .flatMap { authHeader =>
        val tokenPattern = "(?i).*Bearer\\s+(.+)".r
        authHeader match {
          case tokenPattern(token) => Some(token.trim)
          case _                   =>
            println(s"Unexpected Authorization format: $authHeader")
            None
        }
      }
      .getOrElse {
        println(s"Attempted to create bearer token with NIN0: $nino, UTR: $utr, CredID: $credID")
        fail("Could not obtain auth bearer token")
      }
}
