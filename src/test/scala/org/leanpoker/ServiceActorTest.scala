package org.leanpoker

import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest

class ServiceActorTest extends Specification with Specs2RouteTest {
    "The service" should {
        "return the current version" in {
            Post("/") ~> new ServiceActor().route ~> check {
                responseAs[String] === "0.0.1"
            }
        }

        "return 0" in {
            Post("/") ~> new ServiceActor().route ~> check {
                responseAs[String] === "0"
            }
        }
    }
}
