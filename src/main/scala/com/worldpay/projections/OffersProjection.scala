package com.worldpay.projections

import java.time.LocalDateTime

import com.worldpay.domain.Offer

import scala.language.{higherKinds, implicitConversions, postfixOps}

protected[projections] case class OffersProjection(offers: Map[Long, Offer] = Map.empty, nextId : Long = 1L) {
  def create(byId: Long): (BigDecimal, List[String], LocalDateTime) ⇒ OffersProjection =
    (p, ds, d) ⇒ OffersProjection(offers + (byId → Offer(byId, p, ds, d)), byId + 1)

  def update(byId: Long): (Option[BigDecimal], Option[List[String]]) ⇒ OffersProjection =   (p, ds) ⇒
    get(byId).map { updatePrice(p) andThen updateDescriptions(ds) }
      .map{ offer ⇒ copy(offers = offers + (byId → offer)) }.getOrElse(this)

  private def updatePrice(v: Option[BigDecimal]): Offer ⇒ Offer =
    offer ⇒ v.map(p ⇒ offer.copy(price = p)).getOrElse(offer)

  private def updateDescriptions(desc: Option[List[String]]): Offer ⇒ Offer =
    offer ⇒ desc.map(p ⇒ offer.copy(descriptions = p)).getOrElse(offer)

  def all: List[Offer] = offers.values.toList

  def get(byId: Long): Option[Offer] = offers.get(byId)

  def cancel(byId: Long): OffersProjection =
    get(byId).map { offer ⇒ copy(offers = offers + (byId → cancel(offer))) }.getOrElse(this)

  def delete(byId: Long): OffersProjection = get(byId).map { _ ⇒ copy(offers = offers - byId ) }.getOrElse(this)

  private def cancel(offer: Offer) = offer.copy(endDate = LocalDateTime.now())

  override def toString: String = offers.values.toString()
}