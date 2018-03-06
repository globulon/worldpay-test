package com.worldpay

import scala.language.{higherKinds, implicitConversions, postfixOps}

package object translations {
  type |~~>[-A, +B] = Translation[A, B]

  def translate[A, B](f: A ⇒ B): (A |~~> B) = new (A |~~> B) {
    override def apply(a: A): B = f(a)
  }
}
