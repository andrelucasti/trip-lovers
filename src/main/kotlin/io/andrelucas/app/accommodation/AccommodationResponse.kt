package io.andrelucas.app.accommodation

import kotlinx.serialization.Serializable

@Serializable
data class AccommodationResponse(val accommodationId: String,
                                 val name: String,
                                 val latitude: Double,
                                 val longitude: Double,
                                 val type: String,
                                 val estimatePriceInCents: Int,
                                 val servicesOffering: List<String>)
