package com.worldpay.projections

import java.time.LocalDateTime

import akka.japi.Util
import akka.persistence.fsm.PersistentFSM
import akka.persistence.fsm.PersistentFSM.FSMState
import com.worldpay.domain.Offer
import com.worldpay.projections

import scala.concurrent.duration._
import scala.language.{higherKinds, implicitConversions, postfixOps}
import scala.reflect.{ClassTag, classTag}


sealed trait Command
case class CreateOffer(price: BigDecimal, descriptions: List[String], delay: Long) extends Command
case class UpdateOffer(id: Long, price: Option[BigDecimal], descriptions: Option[List[String]]) extends Command
case class CancelOffer(id: Long) extends Command
case class DeleteOffer(id: Long)  extends Command
case class GetOffers() extends Command
case class GetOffer(id: Long) extends Command
case class Shutdown() extends Command

sealed trait View
case class Offers(subset: List[Offer])

sealed trait DomainEvent
case class OfferCreated(id: Long, price: BigDecimal, descriptions: List[String], endDate: LocalDateTime) extends DomainEvent
case class OfferUpdated(id: Long, price: Option[BigDecimal], descriptions: Option[List[String]]) extends DomainEvent
case class OfferDeleted(id: Long) extends DomainEvent
case class OfferCanceled(id: Long) extends DomainEvent

sealed trait ProjectionState extends FSMState
case object Running extends ProjectionState { override def identifier: String = "Running" }


protected[projections] class DomainBus extends PersistentFSM[ProjectionState, OffersProjection, DomainEvent]  {
  override def persistenceId = "DomainBus"

  override def domainEventClassTag: ClassTag[projections.DomainEvent] = classTag[projections.DomainEvent]

  startWith(Running, OffersProjection())

  when(Running) {
    case Event(CreateOffer(p, ds, d), OffersProjection(_, nextId)) ⇒
      stay applying OfferCreated(nextId, p, ds, LocalDateTime.now().plusSeconds(d)) forMax(1 second)
    case Event(UpdateOffer(id, p, ds), projection) ⇒
      projection.get(id).map { _ ⇒ stay applying OfferUpdated(id, p, ds) forMax(1 second) }.getOrElse(stay)
    case Event(DeleteOffer(id), projection) ⇒
      projection.get(id).map { _ ⇒ stay applying OfferDeleted(id) forMax(1 second) }.getOrElse(stay)
    case Event(CancelOffer(id), projection) ⇒
      projection.get(id).map { _ ⇒ stay applying OfferCanceled(id) forMax(1 second) }.getOrElse(stay)
    case Event(GetOffers(), projection) ⇒
      stay replying Offers(projection.all)
    case Event(GetOffer(byId), projection) ⇒
      stay replying Offers(projection.get(byId).toList)
    case Event(Shutdown(), _) ⇒
      stop()
  }

  override def applyEvent(domainEvent: projections.DomainEvent, projection: OffersProjection): OffersProjection = domainEvent match {
    case OfferCreated(id, p, ds, d) ⇒
      val proj = projection.create(id)(p, ds, d)
      sender ! Offers(proj.get(id).toList)
      proj
    case OfferUpdated(id, p, ds) ⇒ projection.update(id)(p, ds)
    case OfferDeleted(byId) ⇒ projection.delete(byId)
    case OfferCanceled(byId) ⇒ projection.cancel(byId)
  }
}
