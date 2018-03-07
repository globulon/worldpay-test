package com.worldpay.http

import java.time.LocalDateTime

import scala.language.{higherKinds, implicitConversions, postfixOps}

sealed trait DTO
case class OfferCreation(price: BigDecimal, descriptions: List[String], duration: Long) extends DTO
case class OfferUpdate(price: Option[BigDecimal], descriptions: Option[List[String]]) extends DTO
case class OfferID(value: Long) extends DTO
case class Offer(id: Long, price: BigDecimal, descriptions: List[String],
                 endDate: LocalDateTime, expired: Boolean = false) extends DTO
case class Processed() extends DTO
case class Error(message: String) extends DTO
case class Found(offers: List[Offer])