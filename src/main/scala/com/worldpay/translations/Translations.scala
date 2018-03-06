package com.worldpay.translations

import scala.language.{higherKinds, implicitConversions, postfixOps}

trait Translation[-A, +B] extends (A ⇒ B)

trait Translations {
 implicit protected class RichTranslation[A](a: A) {
    def translateTo[B](implicit t: (A |~~> B)): B = t(a)
  }
}
