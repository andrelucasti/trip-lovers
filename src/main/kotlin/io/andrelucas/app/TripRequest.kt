package io.andrelucas.app

import java.util.*

data class TripRequest(val title: String,
                       val about: String,
                       val destination: String,
                       val departure: String,
                       val returns: String,
                       val localities: List<LocalityRequest>,
                       val accommodations: List<AccommodationRequest>,
                       val needsVisa: Boolean,
                       val userId: UUID)