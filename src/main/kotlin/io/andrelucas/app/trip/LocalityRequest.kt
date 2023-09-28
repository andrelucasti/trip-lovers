package io.andrelucas.app.trip

import kotlinx.serialization.Serializable

@Serializable
data class LocalityRequest(val name: String,
                           val type: String,
                           val latitude: Double,
                           val longitude: Double,
                           val estimatePriceInCents: Int)