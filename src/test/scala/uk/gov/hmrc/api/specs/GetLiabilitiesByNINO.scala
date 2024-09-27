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
import uk.gov.hmrc.api.testData.TestDataGenerator._
import play.api.libs.json._

class GetLiabilitiesByNINO extends BaseSpec with BaseHelper {

  Feature("Retrieve SA Liabilities details for a valid NINO") {

    Scenario(
      "Retrieve liability details for a given valid NINO with single liability and all fields in response payload"
    ) {
      Given("the SA Liabilities sandpit API is up and running")

      When("user has created a bearer token for a valid nino")
      val nino        = generateNINO()
      checkNINOFormat(nino)
      val bearerToken = authHelper.getAuthBearerToken(nino, generateUTR(), generateCredID())

      And("test data has been populated for the NINO")
      createBalanceDetailsAllFields(nino)

      When(
        "user sends a GET request to retrieve liability details with valid details"
      )
      lazy val response = sa_service.getSALiabilitiesSandpit(nino, s"Bearer $bearerToken")
      println(response)

      Then("the response status code should be 200")
      checkResponseStatus(response.status, 200)

      And(
        "the response body should have the array with balance details as expected including all mandatory & optional fields"
      )
      val responseBody = response.body
      checkSALiabilitiesResponse(responseBody, true)

    }

    Scenario(
      "Retrieve liability details for a given valid NINO with single liability and only mandatory fields"
    ) {
      pending
      Given("the SA Liabilities sandpit API is up and running")

      When("user has created a bearer token for a valid nino")
      val nino        = generateNINO()
      checkNINOFormat(nino)
      val bearerToken = authHelper.getAuthBearerToken(nino, generateUTR(), generateCredID())

      And("test data has been populated for the NINO")
      val reqPayload: JsValue = Json.obj(
        "payableAmount"    -> 9076,
        "pendingDueAmount" -> 1340,
        "overdueAmount"    -> 9293
      )

      createBalanceDetailsSelectedFields(nino, reqPayload)

      When(
        "user sends a GET request to retrieve liability details with valid details"
      )
      lazy val response = sa_service.getSALiabilitiesSandpit(nino, s"Bearer $bearerToken")
      println(response)

      Then("the response status code should be 200")
      checkResponseStatus(response.status, 200)

      And("the response body should have the array with balance details as expected including all optional fields")
      val responseBody = response.body
      checkSALiabilitiesResponse(responseBody)
    }

    Scenario(
      "Retrieve liability details for a given valid NINO with 2 liabilities and all fields in response payload"
    ) {
      Given("the SA Liabilities sandpit API is up and running")

      When("user has created a bearer token for a valid nino")
      val nino        = generateNINO()
      checkNINOFormat(nino)
      val bearerToken = authHelper.getAuthBearerToken(nino, generateUTR(), generateCredID())

      And("test data has been populated for the NINO")

      createBalanceDetailsAllFields(nino)
      createBalanceDetailsAllFields(nino)

      When(
        "user sends a GET request to retrieve liability details with valid details"
      )
      lazy val response = sa_service.getSALiabilitiesSandpit(nino, s"Bearer $bearerToken")
      println(response)

      Then("the response status code should be 200")
      checkResponseStatus(response.status, 200)

      And("the response body should have the array with balance details as expected including all optional fields")
      val responseBody = response.body
      checkBalanceDetailsArrayLength(Json.parse(responseBody), 2)
      checkSALiabilitiesResponse(responseBody)
    }

  }

}
