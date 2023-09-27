package io.andrelucas.repository.trip

import io.andrelucas.business.trip.Trip
import io.andrelucas.business.trip.TripRepository
import java.util.*

class TripInMemoryRepository : TripRepository {
    private val trips = mutableListOf<Trip>()

    override suspend fun save(trip: Trip) {
        trips.add(trip)
    }

    override suspend fun findAll(): List<Trip> {
      return trips
    }

    override suspend fun findById(tripId: UUID): List<Trip> {
        TODO("Not yet implemented")
    }

    override suspend fun update(trip: Trip) {
        TODO("Not yet implemented")
    }
}