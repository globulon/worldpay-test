package com.worldpay.translations

import java.time.LocalDateTime.now

import com.worldpay.{domain, http}
import com.worldpay.projections.Offers

import scala.language.{higherKinds, implicitConversions, postfixOps}

trait OfferTranslations { self: Translations ⇒
  implicit final def offerToDTO: (domain.Offer |~~> http.Offer) = translate {
    case domain.Offer(id, p, desc, endDate) ⇒ http.Offer(id, p, desc, endDate, expired = endDate.isBefore(now()))
  }

  implicit final def offersToFound: (Offers |~~> http.Found) = translate {
    case Offers(all) ⇒ http.Found(all.map(_.translateTo[http.Offer]))
  }
}