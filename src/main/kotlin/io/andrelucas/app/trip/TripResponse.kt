package io.andrelucas.app.trip

import kotlinx.serialization.Serializable

@Serializable
data class TripResponse(val tripId: String,
                        val title: String,
                        val destination: String,
                        val about: String,
                        val departure: String,
                        val returns: String,
                        val localities: List<LocalityResponse>,
                        val accommodations: List<AccommodationResponse>,
                        val needsVisa: Boolean,
                        val estimateTotalPrice: Int,
                        val adults: Int,
                        val userId: String)