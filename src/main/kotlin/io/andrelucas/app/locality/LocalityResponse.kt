package io.andrelucas.app.locality

import io.andrelucas.business.locality.Locality
import kotlinx.serialization.Serializable

@Serializable
data class LocalityResponse(val localityId: String,
                            val name: String,
                            val latitude: Double,
                            val longitude: Double,
                            val type: String,
                            val estimatePriceInCents: Int)





fun Locality.toResponse() = LocalityResponse(
    localityId = this.id.toString(),
    name = this.name,
    latitude = this.coordinates.latitude,
    longitude = this.coordinates.longitude,
    type = this.localityType.name,
    estimatePriceInCents = this.estPrice.valueInDollarInCents()
)
