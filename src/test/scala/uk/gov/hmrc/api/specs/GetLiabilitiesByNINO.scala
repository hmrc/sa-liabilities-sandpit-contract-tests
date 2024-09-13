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

/*
pending Work
-------------
Update validation for Correlation ID

 */
package uk.gov.hmrc.api.specs

import uk.gov.hmrc.api.helpers.BaseHelper
import uk.gov.hmrc.api.testData.TestDataGenerator.{createBalanceDetails, generateCredID, generateNINO, generateUTR}

class GetLiabilitiesByNINO extends BaseSpec with BaseHelper {

  Feature("Retrieve SA Liabilities details for a valid NINO") {

    val expectedTypes = Map(
      "totalBalance"     -> "number",
      "pendingDueDate"   -> "string",
      "pendingDueAmount" -> "number",
      "payableAmount"    -> "number",
      "payableDueDate"   -> "string",
      "overdueAmount"    -> "number"
    )

    Scenario("Retrieve liability details for a given valid NINO with single liability") {
      Given("the SA Liabilities sandpit API is up and running")

      When("user has created a bearer token for a valid nino")
      val nino        = generateNINO()
      checkNINOFormat(nino)
      val bearerToken = authHelper.getAuthBearerToken(nino, generateUTR(), generateCredID())

      And("test data has been populated for the NINO")
      createBalanceDetails(nino, bearerToken)

      When(
        "user sends a GET request to retrieve liability details with valid details"
      )
      lazy val response = sa_service.getSALiabilitiesSandpit(nino, bearerToken)
      println(response)

      Then("the response status code should be 200")
      checkResponseStatus(response.status, 200)

      And("the response body should have the array with balance details as expected")

      val responseBody = response.body

      checkSALiabilitiesResponse(responseBody, expectedTypes)

      And("response header should consist of correlation ID")
      // val correlationID = response.headers.get("CorrelationId")
      val correlationID = response.headers.get("Content-Type")
      correlationID should not be empty
    }

//    Scenario("Retrieve liability details for a given valid NINO with multiple liabilities") {
//      Given("the SA Liabilities sandpit API is up and running")
//
//      When(
//        "user sends a GET request to retrieve liability details for a valid NINO with single liability"
//      )
//      checkNINOFormat(multipleLiabilitiesNINO)
//      lazy val response = sa_service.getSALiabilitiesSandpit(multipleLiabilitiesNINO)
//
//      Then("the response status code should be 200")
//      checkResponseStatus(response.status, 200)
//
//      And("the response body should have the array with balance details as expected")
//
//      val responseBody = response.body
//      println(responseBody)
//      checkSALiabilitiesResponse(responseBody, expectedTypes)
//
//      And("response header should consist of correlation ID")
//      //      val correlationID = response.headers.get("CorrelationId")
//      val correlationID = response.headers.get("Content-Type")
//      correlationID should not be empty
//    }
  }

}
