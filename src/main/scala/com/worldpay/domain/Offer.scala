package com.worldpay.domain

import java.time.LocalDateTime

import scala.language.{higherKinds, implicitConversions, postfixOps}

sealed trait Entity {
  def id: Long
}

case class Offer(override val id: Long, price: BigDecimal, descriptions: List[String], endDate: LocalDateTime) extends Entity