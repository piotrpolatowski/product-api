/**
 * Copyright 2011-2016 GatlingCorp (http://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class PaginatedListProductsSimulation extends Simulation {

  val itemsInDatabase = 100000
  val pageSize = 3
  val maxPage = itemsInDatabase / pageSize
  val feeder = Iterator.continually(Map("page" -> s"${maxPage}"))

  val httpConf = http
    .baseURL("http://localhost:8088") // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val headers_10 = Map("Content-Type" -> "application/x-www-form-urlencoded") // Note the headers specific to a given request

  val scn = scenario("Scenario Name") // A scenario is a chain of requests and pauses
  .feed(feeder)
    .exec(http("request_1")
      .get("/products/list?page=${page}"))
    .pause(7) // Note that Gatling has recorded real time pauses

  setUp(scn.inject(rampUsersPerSec(20) to (400) during (5 minutes)).protocols(httpConf))
}
