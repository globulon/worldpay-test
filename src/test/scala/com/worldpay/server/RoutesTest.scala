package com.worldpay.server

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.ContentTypes.`application/json`
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.Timeout
import com.wordplay.fixtures.Stunts
import com.worldpay.http.{DTOFormats, Offer, Processed}
import org.scalatest.{MustMatchers, WordSpec}
import akka.http.scaladsl.model.StatusCodes._

import scala.concurrent.duration._
import scala.language.{higherKinds, implicitConversions, postfixOps}

final class RoutesTest  extends WordSpec with MustMatchers with  ScalatestRouteTest with Routes  with Stunts with DTOFormats {
  implicit private def timeout: Timeout = 1 second

  "Service" should {
    "POST offer" in {
      Post("/offer", HttpEntity(`application/json`, """{}""")) ~> routes(app) ~> check {
        status must be (OK)
        responseAs[Offer] must be(Offer())
      }
    }

    "PUT offer" in {
      Put("/offer/123", HttpEntity(`application/json`, """{}""")) ~> routes(app) ~> check {
        status must be (Accepted)
        responseAs[Processed] must be(Processed())
      }
    }

    "DELETE offer" in {
      Delete("/offer/123") ~> routes(app) ~> check {
        status must be (Accepted)
        responseAs[Processed] must be(Processed())
      }
    }

    "GET offer" in {
      Get("/offer/123") ~> routes(app) ~> check {
        status must be (OK)
        responseAs[Offer] must be(Offer())
      }
    }

    "GET offers" in {
      Get("/offer") ~> routes(app) ~> check {
        status must be (OK)
        responseAs[List[Offer]] must be(List(Offer()))
      }
    }
  }

  private def app = Application(echo(system))
}
