package io.andrelucas.app

import java.util.UUID

data class TripRequest(val title: String,
                       val about: String,
                       val departure: String,
                       val returns: String,
                       val localities: List<LocalityRequest>,
                       val accommodations: List<AccommodationRequest>,
                       val needsVisa: Boolean,
                       val userId: UUID)