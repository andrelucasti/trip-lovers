package io.andrelucas.configuration.trip

import io.andrelucas.app.TripService
import io.andrelucas.app.trip.createTrip
import io.andrelucas.business.trip.TripRepository
import io.andrelucas.repository.trip.TripDaoInMemory
import io.andrelucas.repository.trip.TripInMemoryRepository
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.tripModule() {
    routing {
        val tripRepository: TripRepository = TripInMemoryRepository()
        createTrip(TripService(tripRepository, TripDaoInMemory(tripRepository)))
    }
}