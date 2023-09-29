package io.andrelucas.app.trip

import io.andrelucas.app.accommodation.AccommodationRequest
import io.andrelucas.app.locality.LocalityRequest
import io.andrelucas.app.vehicle.VehicleRequest
import kotlinx.serialization.Serializable

@Serializable
data class TripRequest(val title: String,
                       val about: String,
                       val destination: String,
                       val departure: String,
                       val returns: String,
                       val localities: List<LocalityRequest>,
                       val accommodations: List<AccommodationRequest>,
                       val vehicles: List<VehicleRequest>,
                       val needsVisa: Boolean,
                       val adults: Int,
                       val userId: String)