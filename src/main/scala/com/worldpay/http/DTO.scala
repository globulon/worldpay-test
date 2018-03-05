package com.worldpay.http

import scala.language.{higherKinds, implicitConversions, postfixOps}

sealed trait DTO
case class OfferDescription() extends DTO
case class OfferID(value: Long) extends DTO
case class Offer() extends DTO
case class Processed() extends DTO