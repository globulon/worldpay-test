package com.worldpay.server

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.{HttpEntity, _}
import akka.http.scaladsl.server.Directives.{as, complete, entity, get, onComplete, path, post, _}
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import com.worldpay.http
import com.worldpay.http._
import spray.json._

import scala.concurrent.ExecutionContext
import scala.language.{higherKinds, implicitConversions, postfixOps}

trait Routes extends DTOFormats with DefaultJsonProtocol {
  final protected def routes(app: Application)(implicit ec: ExecutionContext, d: Timeout): Route =
    index ~ postOffer(app) ~ putOffer(app) ~ deleteOffer(app) ~ getOffer(app) ~ getOffers(app)

  private def index = path("hello") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
    }
  }

  private def postOffer(app: Application)(implicit ec: ExecutionContext, d: Timeout): Route = path("offer") {
    post {
      entity(as[OfferCreation]) { request ⇒
        onComplete(app.controller ? Push(request)) {
          case util.Success(http.Found(List(offer))) ⇒ complete((OK, List(offer)))
          case util.Success(res) ⇒ complete((NotAcceptable, Error(s"Unexpected result $res")))
          case util.Failure(exception) ⇒ complete((InternalServerError, Error(exception.getMessage)))
        }
      }
    }
  }

  private def putOffer(app: Application)(implicit ec: ExecutionContext, d: Timeout): Route = pathPrefix("offer") {
    path(LongNumber) { value ⇒
      put {
        entity(as[OfferUpdate]) { desc ⇒
          complete {
            app.controller ! Update(OfferID(value), desc)
            (Accepted, Processed())
          }
        }
      }
    }
  }


  private def deleteOffer(app: Application)(implicit ec: ExecutionContext, d: Timeout): Route = pathPrefix("offer") {
    path(LongNumber) { value ⇒
      delete {
        complete {
          app.controller ! Delete(OfferID(value))
          (Accepted, Processed())
        }
      }
    }
  }

  private def getOffer(app: Application)(implicit ec: ExecutionContext, d: Timeout): Route = pathPrefix("offer") {
    path(LongNumber) { value ⇒
      get {
        onComplete(app.controller ? Get(OfferID(value))) {
          case util.Success(http.Found(Nil)) ⇒ complete((NotFound, Nil))
          case util.Success(http.Found(offers)) ⇒ complete((OK, offers))
          case util.Success(_) ⇒ complete((NotAcceptable, Error("Unexpected result")))
          case util.Failure(exception) ⇒ complete((InternalServerError, Error(exception.getMessage)))
        }
      }
    }
  }

  private def getOffers(app: Application)(implicit ec: ExecutionContext, d: Timeout): Route = path("offer") {
    pathEnd {
      get {
        onComplete(app.controller ? GetAll()) {
          case util.Success(http.Found(offers)) ⇒ complete((OK, offers))
          case util.Success(_) ⇒ complete((NotAcceptable, Error("Unexpected result")))
          case util.Failure(exception) ⇒ complete((InternalServerError, Error(exception.getMessage)))
        }
      }
    }
  }
}