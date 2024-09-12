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

///*
// * Copyright 2024 HM Revenue & Customs
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package uk.gov.hmrc.api.helpers
//
//import io.circe.parser
//import io.circe.generic.auto._
//import scala.io.Source
//import scala.util.Using
//import io.circe.Decoder
//
//case class TestData(nino: String, utr: String, credID: String)
//
//object testDataLoader {
//  def loadData(): Map[String, TestData] = {
//    val filePath = "src/test/scala/uk/gov/hmrc/api/json/testData.json"
//    val json     = Using(Source.fromFile(filePath)) { source =>
//      source.getLines().mkString
//    }.getOrElse(throw new Exception(s"Failed to read file at $filePath"))
//
//    val result = parser.decode[Map[String, TestData]](json)
//    result match {
//      case Right(data) => data
//      case Left(error) => throw new Exception(s"Failed to parse JSON: $error")
//    }
//  }
//}
