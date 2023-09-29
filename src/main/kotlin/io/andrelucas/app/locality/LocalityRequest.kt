package io.andrelucas.app.locality

import io.andrelucas.business.Coordinates
import io.andrelucas.business.Currency
import io.andrelucas.business.Price
import io.andrelucas.business.locality.Locality
import io.andrelucas.business.locality.LocalityType
import kotlinx.serialization.Serializable

@Serializable
data class LocalityRequest(val name: String,
                           val type: String,
                           val latitude: Double,
                           val longitude: Double,
                           val estimatePriceInCents: Int)

fun LocalityRequest.toLocality() = Locality.create (
    name = this.name,
    localityType = LocalityType.valueOf(this.type),
    coordinates = Coordinates(this.latitude, this.longitude),
    estPrice = Price(this.estimatePriceInCents, Currency.USD),
    address = "",
)