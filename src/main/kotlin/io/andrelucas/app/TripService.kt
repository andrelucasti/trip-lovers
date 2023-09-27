package io.andrelucas.app

import io.andrelucas.business.coordinates.Coordinates
import io.andrelucas.business.trip.*
import io.andrelucas.business.trip.Currency
import java.time.LocalDate
import java.util.*

class TripService(private val tripRepository: TripRepository,
                  private val tripDao: TripDao) {
    suspend fun create(tripRequest: TripRequest) =
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

    suspend fun findAllFutureTrips(currentDate: LocalDate, userId: UUID) =
        tripDao.findAllFutureTrips(currentDate, userId).sortedByDescending { it.departure }


    private fun createLocalities(localities: List<LocalityRequest>) =
        localities.map {
            Locality.create(
                it.name,
                LocalityType.valueOf(it.type),
                Coordinates(it.latitude, it.longitude),
                "",
                Price(0, Currency.USD)
            )
        }

    private fun createAccommodations(accommodations: List<AccommodationRequest>) =
        accommodations.map {
            Accommodation.create(
                it.name,
                AccommodationType.valueOf(it.type),
                Coordinates(it.latitude, it.longitude),
                "",
                Price(0, Currency.USD),
                it.servicesOffering
            )
        }
}