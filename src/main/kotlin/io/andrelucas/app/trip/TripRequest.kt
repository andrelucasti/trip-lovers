package io.andrelucas.app.trip

import io.andrelucas.app.accommodation.AccommodationRequest
import io.andrelucas.app.accommodation.toAccommodation
import io.andrelucas.app.locality.LocalityRequest
import io.andrelucas.app.locality.toLocality
import io.andrelucas.app.vehicle.VehicleRequest
import io.andrelucas.app.vehicle.toVehicle
import io.andrelucas.business.trip.Trip
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.UUID

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

fun TripRequest.toTrip() =
    Trip.create(
        title = this.title,
        destination = this.destination,
        about = this.about,
        departure = LocalDate.parse(this.departure),
        returns = LocalDate.parse(this.returns),
        localities = this.localities.map { localityRequest -> localityRequest.toLocality() },
        accommodations = this.accommodations.map { accommodationRequest -> accommodationRequest.toAccommodation() },
        vehicles = this.vehicles.map { vehicleRequest -> vehicleRequest.toVehicle(this.adults) },
        needsVisa = this.needsVisa,
        adults = this.adults,
        userId = UUID.fromString(this.userId)
    )
