package io.andrelucas.app.accommodation

import io.andrelucas.business.accommodation.Accommodation
import kotlinx.serialization.Serializable

@Serializable
data class AccommodationResponse(val accommodationId: String,
                                 val name: String,
                                 val latitude: Double,
                                 val longitude: Double,
                                 val type: String,
                                 val estimatePriceInCents: Int,
                                 val servicesOffering: List<String>)


fun Accommodation.toResponse() = AccommodationResponse(
    accommodationId = this.id.toString(),
    name = this.name,
    latitude = this.coordinates.latitude,
    longitude = this.coordinates.longitude,
    type = this.accommodationType.name,
    estimatePriceInCents = this.estPrice.valueInDollarInCents(),
    servicesOffering = this.servicesOffering
)