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
1. Check missing NINO count as invalid path parameter
2. Update validation for errorCode, errorDescription and correlation ID
3. Add a NINO which doesn't exist in the system
4. Additional scenarios to be added
 */

package uk.gov.hmrc.api.specs

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.api.helpers.BaseHelper

class ErrorScenarios extends BaseSpec with BaseHelper {

  Feature("Validate Error Scenarios") {
    val invalidNINOFormat: String = "invalid"
    val specialCharInNINO: String = "AA000000A:"
    val NonExistentNINO           = "AB000000A"


    Scenario("Validation of error code and error description for NINO with invalid format") {
      Given("the SA Liabilities sandpit API is up and running")

      And(s"user sends a GET request to retrieve liability details for a NINO wih invalid format")
      // lazy val response = sa_service.getSALiabilitiesSandpit(invalidNINOFormat)

      Then("the error response should be 400")
      // checkResponseStatus(response.status, 400)

      And("the errorCode should be set to 1113")
      // val responseBody: JsValue = Json.parse(response.body)
      // (responseBody \ "errorCode").as[String] shouldEqual "1113"

      And("the errorDescription should be set to Invalid path parameters")
      // (responseBody \ "errorDescription").as[String] shouldEqual "Invalid NINO format" // Invalid path parameters

      // And("response header should consist of correlation ID")
//    val correlationID = response.headers.get("CorrelationId")
      // val correlationID = response.headers.get("Content-Type")
      // correlationID should not be empty

    }
    Scenario("Validation of error code and error description for NINO with special character") {
      Given("the SA Liabilities sandpit API is up and running")

      And(s"user sends a GET request to retrieve liability details for a NINO with special character")
      // lazy val response = sa_service.getSALiabilitiesSandpit(specialCharInNINO)

      Then("the error response should be 400")
      // checkResponseStatus(response.status, 400)

      And("the errorCode should be set to 1113")
      // val responseBody: JsValue = Json.parse(response.body)
      // (responseBody \ "errorCode").as[String] shouldEqual "1113"

      And("the errorDescription should be set to Invalid path parameters")
      // (responseBody \ "errorDescription").as[String] shouldEqual "Invalid NINO format" // Invalid path parameters

      And("response header should consist of correlation ID")
      //    val correlationID = response.headers.get("CorrelationId")
      // val correlationID = response.headers.get("Content-Type")
      // correlationID should not be empty

    }

    Scenario("Validation of error code and error description for non-existent NINO") {
      Given("the SA Liabilities sandpit API is up and running")

      When("user sends a GET request to retrieve liability details for a non-existent NINO")
      checkNINOFormat(NonExistentNINO)
      // lazy val response = sa_service.getSALiabilitiesSandpit(NonExistentNINO)

      Then("the error response should be 400")
      // checkResponseStatus(response.status, 400)

      And("the errorCode should be set to 1113")
      // val responseBody: JsValue = Json.parse(response.body)
      // (responseBody \ "errorCode").as[String] shouldEqual "1002"

      And("the errorDescription should be set to Invalid path parameters")
      // (responseBody \ "errorDescription").as[String] shouldEqual "NINO not found"

      And("response header should consist of correlation ID")
      //      val correlationID = response.headers.get("CorrelationId")
      // val correlationID = response.headers.get("Content-Type")
      // correlationID should not be empty
    }

  }
}
