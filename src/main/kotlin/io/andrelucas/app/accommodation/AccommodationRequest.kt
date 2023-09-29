package io.andrelucas.app.accommodation

import kotlinx.serialization.Serializable

@Serializable
data class AccommodationRequest(val name: String,
                                val type: String,
                                val latitude: Double,
                                val longitude: Double,
                                val estimatePriceInCents: Int,
                                val servicesOffering: List<String>)