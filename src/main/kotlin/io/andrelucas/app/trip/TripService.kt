package io.andrelucas.app.trip

import io.andrelucas.business.trip.TripDao
import io.andrelucas.business.trip.TripRepository
import java.time.LocalDate
import java.util.*

class TripService(private val tripRepository: TripRepository,
                  private val tripDao: TripDao) {
    suspend fun create(tripRequest: TripRequest) =
        tripRequest.toTrip()
            .let { tripRepository.save(it) }

    suspend fun findAll() =
        tripRepository.findAll().map { trip ->  trip.toResponse() }


    suspend fun findAllFutureTrips(currentDate: LocalDate, userId: UUID) =
        tripDao.findAllFutureTrips(currentDate, userId).sortedByDescending { it.departure }

    suspend fun findByDestination(destination: String): List<TripResponse> {
        return tripDao.findTripsByDestination(destination).map { trip -> trip.toResponse() }
    }
}
