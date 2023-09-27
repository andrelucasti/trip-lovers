package io.andrelucas.business.trip

import java.util.UUID

interface TripRepository {
    suspend fun save(trip: Trip)
    suspend fun findAll(): List<Trip>
    suspend fun findById(tripId: UUID): List<Trip>
    suspend fun update(trip: Trip)
}