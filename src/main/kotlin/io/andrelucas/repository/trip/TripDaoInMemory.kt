package io.andrelucas.repository.trip

import io.andrelucas.business.trip.Trip
import io.andrelucas.business.trip.TripDao
import io.andrelucas.business.trip.TripRepository
import java.time.LocalDate
import java.util.*

class TripDaoInMemory(private val tripRepository: TripRepository) : TripDao {
    override suspend fun findAllFutureTrips(currentDate: LocalDate, userId: UUID): List<Trip> {
        return tripRepository.findAll().filter { it.userId == userId && it.departure.isAfter(currentDate) }
    }
}