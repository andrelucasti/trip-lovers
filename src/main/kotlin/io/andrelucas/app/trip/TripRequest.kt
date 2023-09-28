package io.andrelucas.app.trip

import kotlinx.serialization.Serializable

@Serializable
data class TripRequest(val title: String,
                       val about: String,
                       val destination: String,
                       val departure: String,
                       val returns: String,
                       val localities: List<LocalityRequest>,
                       val accommodations: List<AccommodationRequest>,
                       val needsVisa: Boolean,
                       val adults: Int,
                       val userId: String)