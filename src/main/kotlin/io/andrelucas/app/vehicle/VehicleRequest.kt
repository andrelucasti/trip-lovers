package io.andrelucas.app.vehicle

import kotlinx.serialization.Serializable

@Serializable
data class VehicleRequest(val company: String,
                          val type: String,
                          val estimatePriceInCents: Int,
                          val servicesOffering: List<String>)