package io.andrelucas.app

import io.andrelucas.business.trip.*
import java.time.LocalDate

class TripService(private val tripRepository: TripRepository) {
    suspend fun create(tripRequest: TripRequest) {
        Trip.create(tripRequest.title,
            tripRequest.destination,
            tripRequest.about,
            LocalDate.parse(tripRequest.departure),
            LocalDate.parse(tripRequest.returns),
            createLocalities(tripRequest.localities),
            createAccommodations(tripRequest.accommodations),
            tripRequest.needsVisa,
            tripRequest.userId)
            .let {
                tripRepository.save(it)
            }
    }

    private fun createLocalities(localities: List<LocalityRequest>) =
        localities.map {
            Locality.create(
                it.name,
                LocalityType.valueOf(it.type),
                it.latitude,
                it.longitude,
                "",
                0
            )
        }

    private fun createAccommodations(accommodations: List<AccommodationRequest>) =
        accommodations.map {
            Accommodation.create(
                it.name,
                AccommodationType.valueOf(it.type),
                it.latitude,
                it.longitude,
                "",
                0,
                it.servicesOffering
            )
        }
}