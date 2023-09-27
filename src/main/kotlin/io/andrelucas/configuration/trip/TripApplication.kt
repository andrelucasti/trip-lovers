package io.andrelucas.configuration.trip

import io.andrelucas.app.TripService
import io.andrelucas.app.trip.createTrip
import io.andrelucas.repository.trip.TripInMemoryRepository
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.tripModule() {
    routing {
        createTrip(TripService(TripInMemoryRepository()))
    }
}