package io.andrelucas.app.locality

import kotlinx.serialization.Serializable

@Serializable
data class LocalityResponse(val localityId: String,
                            val name: String,
                            val latitude: Double,
                            val longitude: Double,
                            val type: String,
                            val estimatePriceInCents: Int)
