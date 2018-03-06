package com.worldpay.translations

import com.worldpay.http
import com.worldpay.domain
import com.worldpay.projections.Offers

import scala.language.{higherKinds, implicitConversions, postfixOps}

trait OfferTranslations { self: Translations ⇒
  implicit final def domainToHttp: (domain.Offer |~~> http.Offer) = translate {
    case domain.Offer(id, p, descs, endDate) ⇒ http.Offer(id, p, descs, endDate)
  }

  implicit final def offersToFound: (Offers |~~> http.Found) = translate {
    case Offers(all) ⇒ http.Found(all.map(_.translateTo[http.Offer]))
  }

}
