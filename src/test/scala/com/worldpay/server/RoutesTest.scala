package com.worldpay.server

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.ContentTypes.`application/json`
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.Timeout
import com.wordplay.fixtures.{DTOs, Stunts}
import com.worldpay.http.{DTOFormats, Offer, Processed}
import org.scalatest.{MustMatchers, WordSpec}

import scala.concurrent.duration._
import scala.language.{higherKinds, implicitConversions, postfixOps}

final class RoutesTest extends WordSpec
  with MustMatchers with ScalatestRouteTest with Routes with Stunts with DTOFormats with DTOs with Bootstrap {
  implicit private def timeout: Timeout = 1 second

  private val app = Application(controller(system, timeout))


  "Service" should {
    "POST offer" in {
      Post("/offer", HttpEntity(`application/json`, offerCreation.compactPrint)) ~> routes(app) ~> check {
        status must be(OK)
        responseAs[List[Offer]] must have size 1
        val List(offer) = responseAs[List[Offer]]
        offer.price must be(BigDecimal(250))
        offer.descriptions must be(List("ABC", "EFG"))
      }
    }

    "PUT offer" in {
      Post("/offer", HttpEntity(`application/json`, offerCreation.compactPrint)) ~> routes(app) ~> check {
        status must be(OK)
        val List(offer) = responseAs[List[Offer]]
        Put(s"""/offer/${offer.id}""", HttpEntity(`application/json`, offerUpdate.prettyPrint)) ~> routes(app) ~> check {
          status must be(Accepted)
          responseAs[Processed] must be(Processed())
        }

        Get(s"""/offer/${offer.id}""") ~> routes(app) ~> check {
          val List(updated) = responseAs[List[Offer]]
          updated.price must be(BigDecimal(400))
          updated.descriptions must be(List("VWX"))
        }
      }
    }

    "GET all offers" in {
      val size = offers.size

      Range(1, 10) foreach { _ ⇒
        Post("/offer", HttpEntity(`application/json`, offerCreation.compactPrint)) ~> routes(app) ~> check {
          status must be(OK)
        }
      }

      offers.size must be(size + 9)
    }

    "DELETE offer" in {
      val size = offers.size

      Post("/offer", HttpEntity(`application/json`, offerCreation.compactPrint)) ~> routes(app) ~> check {
        status must be(OK)
        val List(offer) = responseAs[List[Offer]]

        offers.size must be(size + 1)

        Delete(s"""/offer/${offer.id}""") ~> routes(app) ~> check {
          status must be(Accepted)
        }

        offers.size must be(size)
      }
    }

    "Cancel offer" in {
      Post("/offer", HttpEntity(`application/json`, offerCreation.compactPrint)) ~> routes(app) ~> check {
        status must be(OK)
        val List(offer) = responseAs[List[Offer]]

        Put(s"""/offer/${offer.id}/cancellation""") ~> routes(app) ~> check {
          status must be(Accepted)
        }
        Range(1, 50).flatMap { _ ⇒ getOffer(offer.id).map(_.expired) }.find(_ == true) must be ('defined)
      }
    }

  }

  private def offers = Get("/offer") ~> routes(app) ~> check {
    status must be(OK)
    responseAs[List[Offer]]
  }

  private def getOffer(id: Long): Option[Offer] = Get(s"""/offer/$id""") ~> routes(app) ~> check {
    status must be(OK)
    responseAs[List[Offer]].headOption
  }

}
