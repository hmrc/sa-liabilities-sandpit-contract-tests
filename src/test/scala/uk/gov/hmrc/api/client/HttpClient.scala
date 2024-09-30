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

package uk.gov.hmrc.api.client

import akka.actor.ActorSystem
import play.api.libs.ws.StandaloneWSResponse
import play.api.libs.ws.ahc.{AhcConfigBuilder, StandaloneAhcWSClient}

import scala.concurrent.duration.*
import scala.concurrent.{Await, Awaitable}
import scala.language.postfixOps
import play.api.libs.ws.DefaultBodyWritables.writeableOf_String

trait HttpClient {

  val builder: AhcConfigBuilder                = new AhcConfigBuilder()
  implicit val system: ActorSystem             = ActorSystem()
  implicit val wsClient: StandaloneAhcWSClient = StandaloneAhcWSClient()

  def getUrl(url: String, requestHeaders: Option[Seq[(String, String)]] = None)(implicit
    client: StandaloneAhcWSClient
  ): StandaloneWSResponse = {

    val request = client.url(url)
    println(s"GET $url\nHeaders: $requestHeaders")

    val response = requestHeaders match {
      case Some(h) => request.withHttpHeaders(h: _*).get()
      case None    => request.get()
    }

    val result = Await.result(response, 12.seconds)
    println(s"Response status: ${result.status}")
    println(s"Response body: ${result.body}")

    result
  }

  def postUrl(
    url: String,
    body: String,
    requestHeaders: Option[Seq[(String, String)]] = None
  )(implicit client: StandaloneAhcWSClient): StandaloneWSResponse = {

    val request = client.url(url)
    println(s"POST $url\nHeaders: $requestHeaders")

    val response = requestHeaders match {
      case Some(h) => request.withHttpHeaders(h: _*).post(body)
      case None    => request.post(body)
    }

    val result = Await.result(response, 12.seconds)
    println(s"Response status: ${result.status}")
    println(s"Response body: ${result.body}")
    result

  }

  def putUrl(
    url: String,
    body: String,
    requestHeaders: Option[Seq[(String, String)]] = None
  )(implicit client: StandaloneAhcWSClient): StandaloneWSResponse = {

    val request = client.url(url)
    println(s"PUT $url\nHeaders: $requestHeaders Body: $body")

    val response = requestHeaders match {
      case Some(h) => request.withHttpHeaders(h: _*).put(body)
      case None    => request.put(body)
    }

    val result = Await.result(response, 12.seconds)
    println(s"Response status: ${result.status}")
    println(s"Response body: ${result.body}")
    result

  }

  protected val awaitableTimeout: FiniteDuration = 30 seconds
  def await[A](f: Awaitable[A]): A               = Await.result(f, awaitableTimeout)
}
