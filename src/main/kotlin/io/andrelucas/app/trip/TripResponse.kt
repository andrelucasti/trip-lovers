package io.andrelucas.app.trip

import io.andrelucas.app.accommodation.AccommodationResponse
import io.andrelucas.app.accommodation.toResponse
import io.andrelucas.app.locality.LocalityResponse
import io.andrelucas.app.locality.toResponse
import io.andrelucas.business.trip.Trip
import kotlinx.serialization.Serializable
import java.time.format.DateTimeFormatter

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

fun Trip.toResponse() = TripResponse(
    tripId = this.id.toString(),
    title = this.title,
    destination = this.destination,
    about = this.about,
    departure = this.departure.format(DateTimeFormatter.ISO_LOCAL_DATE),
    returns = this.returns.format(DateTimeFormatter.ISO_LOCAL_DATE),
    localities = this.localities.map { locality -> locality.toResponse() },
    accommodations = this.accommodations.map { accommodation -> accommodation.toResponse() },
    needsVisa = this.needsVisa,
    estimateTotalPrice = this.estTotalPrice.valueInDollarInCents(),
    adults = this.adults,
    userId = this.userId.toString()
)