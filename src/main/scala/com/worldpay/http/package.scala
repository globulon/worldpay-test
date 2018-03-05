package com.worldpay

import com.worldpay.actors.RichActor

import scala.language.{higherKinds, implicitConversions, postfixOps}

package object http {
  def controller: RichActor = new Controller
}
