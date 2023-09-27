package io.andrelucas.business.trip

import java.time.LocalDate
import java.util.*

interface TripDao {
    suspend fun findAllFutureTrips(currentDate: LocalDate, userId: UUID): List<Trip>
}