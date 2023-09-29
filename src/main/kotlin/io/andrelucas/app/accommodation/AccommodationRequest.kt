package io.andrelucas.app.accommodation

import io.andrelucas.business.Coordinates
import io.andrelucas.business.Currency
import io.andrelucas.business.Price
import io.andrelucas.business.accommodation.Accommodation
import io.andrelucas.business.accommodation.AccommodationType
import kotlinx.serialization.Serializable

@Serializable
data class AccommodationRequest(val name: String,
                                val type: String,
                                val latitude: Double,
                                val longitude: Double,
                                val estimatePriceInCents: Int,
                                val servicesOffering: List<String>)

fun AccommodationRequest.toAccommodation() = Accommodation.create(
    name = this.name,
    accommodationType = AccommodationType.valueOf(this.type),
    coordinates = Coordinates(this.latitude, this.longitude),
    address = "",
    estPrice = Price(this.estimatePriceInCents, Currency.USD),
    servicesOffering = this.servicesOffering
)