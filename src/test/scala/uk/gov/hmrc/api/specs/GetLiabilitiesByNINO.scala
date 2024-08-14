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

package uk.gov.hmrc.api.specs

import uk.gov.hmrc.api.helpers.BaseHelper

class GetLiabilitiesByNINO extends BaseSpec with BaseHelper {

  Feature("Retrieve SA Liabilities details for a valid NINO") {

    val singleLiability   = "AA000000A"
    val multipleLiability = "AA000000D"

    val expectedTypes = Map(
      "totalBalance"     -> "number",
      "pendingDueDate"   -> "string",
      "pendingDueAmount" -> "number",
      "payableAmount"    -> "number",
      "overdueAmount"    -> "number",
      "payableDueDate"   -> "string"
    )

    Scenario("Retrieve liability details for a given valid NINO with single liability") {
      Given("the SA Liabilities sandpit API is up and running")

      When(
        "user sends a GET request to retrieve liability details for a valid NINO with single liability"
      )
      lazy val response = sa_service.getSALiabilitiesSandpit(singleLiability)

      Then("the response status code should be 200")
      checkResponseStatus(response.status, 200)

      And("the response body should have the array with balance details as expected")

      val responseBody = response.body
      checkSALiabilitiesResponse(responseBody, expectedTypes)
    }

    Scenario("Retrieve liability details for a given valid NINO with multiple liability") {
      Given("the SA Liabilities sandpit API is up and running")

      When(
        "user sends a GET request to retrieve liability details for a valid NINO with multiple liability"
      )
      lazy val response = sa_service.getSALiabilitiesSandpit(multipleLiability)

      Then("the response status code should be 200")
      checkResponseStatus(response.status, 200)

      And("the response body should have the array with balance details as expected")

      val responseBody = response.body
      checkSALiabilitiesResponse(responseBody, expectedTypes)
    }
  }
}
