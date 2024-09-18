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

import play.api.libs.json.*
import play.api.libs.ws.StandaloneWSResponse
import uk.gov.hmrc.api.helpers.BaseHelper
import uk.gov.hmrc.api.testData.TestDataGenerator._

class ErrorScenarios extends BaseSpec with BaseHelper {

  Feature("Validate Error Scenarios") {

    Scenario("Validation of error code and error description for NINO with invalid format") {
      Given("the SA Liabilities sandpit API is up and running")

      When("user has created a bearer token for a valid nino")
      val nino        = generateNINO()
      checkNINOFormat(nino)
      val bearerToken = authHelper.getAuthBearerToken(nino, generateUTR(), generateCredID())

      When(
        "user sends a GET request to retrieve liability details with valid details"
      )
      val response: StandaloneWSResponse = sa_service.getSALiabilitiesSandpit(s"$nino:", s"Bearer $bearerToken")
      println(response)

      val responseBody: String = response.body

      val responseBodyJs: JsValue = Json.parse(responseBody)
      val errorCode               = (responseBodyJs \ "errorCode").asOpt[String]
      val errorDescription        = (responseBodyJs \ "errorDescription").asOpt[String]

      Then("the error response should be 400")
      checkResponseStatus(response.status, 400)

      And("the errorCode should be set to 1113")
      errorCode shouldBe Some("1113")

      And("the errorDescription should be set to Invalid path parameters")
      errorDescription shouldBe Some("Invalid path parameters")

      And("response header should consist of correlation ID")
      val correlationID = response.headers.get("CorrelationId")
      correlationID should not be empty

    }

    Scenario("Validation of error code and error description for non-existent NINO") {
      Given("the SA Liabilities sandpit API is up and running")

      When("user has created a bearer token for a valid nino")
      val nino        = generateNINO()
      checkNINOFormat(nino)
      val bearerToken = authHelper.getAuthBearerToken(nino, generateUTR(), generateCredID())

      When("user sends a GET request to retrieve liability details for a non-existent NINO")
      val response: StandaloneWSResponse = sa_service.getSALiabilitiesSandpit(nino, s"Bearer $bearerToken")
      println(response)

      val responseBody: String = response.body
      println(responseBody)

      val responseBodyJs: JsValue = Json.parse(responseBody)
      val errorCode               = (responseBodyJs \ "errorCode").asOpt[String]
      val errorDescription        = (responseBodyJs \ "errorDescription").asOpt[String]

      Then("the error response should be 400")
      checkResponseStatus(response.status, 400)

      And("the errorCode should be set to 1113")
      errorCode shouldBe Some("1002")

      And("the errorDescription should be set to Invalid path parameters")
      errorDescription shouldBe Some("NINO not found")

      And("response header should consist of correlation ID")
      val correlationID = response.headers.get("CorrelationId")
      correlationID should not be empty
    }

    Scenario("Validation of error response for invalid bearer token") {
      Given("the SA Liabilities sandpit API is up and running")

      When("user sends a GET request to retrieve liability details for a non-existent NINO")
      val nino                           = generateNINO()
      checkNINOFormat(nino)
      val response: StandaloneWSResponse = sa_service.getSALiabilitiesSandpit(nino, s"Bearer test")
      println(response)

      Then("the error response should be 400")
      checkResponseStatus(response.status, 401)

      And("the error message should be Unauthorized")
      val responseBodyJs = Json.parse(response.body)
      (responseBodyJs \ "message").as[String] shouldEqual "Invalid bearer token"

    }

    Scenario("Validation of error response for invalid bearer token format") {
      Given("the SA Liabilities sandpit API is up and running")

      When("user has created a bearer token for a valid nino")
      val nino        = generateNINO()
      checkNINOFormat(nino)
      val bearerToken = authHelper.getAuthBearerToken(nino, generateUTR(), generateCredID())

      When("user sends a GET request to retrieve liability details with invalid format for bearer token")
      val response: StandaloneWSResponse = sa_service.getSALiabilitiesSandpit(nino, s"Bear $bearerToken")
      println(response)

      Then("the error response should be 401")
      checkResponseStatus(response.status, 401)

      And("the error message should be Unauthorized")
      val responseBodyJs = Json.parse(response.body)
      (responseBodyJs \ "message").as[String] shouldEqual "Invalid bearer token"
    }

    Scenario("Validation of error response for expired bearer token")(pending)

    Scenario("Validation of error response for missing mandatory fields")(pending)

    Scenario("Validation of error response for invalid data types")(pending)

    Scenario("Validation of error response for empty mandatory fields")(pending)

    Scenario("Validation of error response for empty optional fields")(pending)

    Scenario("Validation of error response for additional fields in response")(pending)

  }
}
