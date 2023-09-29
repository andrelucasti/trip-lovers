package io.andrelucas.configuration.trip

import io.andrelucas.app.trip.TripService
import io.andrelucas.app.trip.allTrips
import io.andrelucas.app.trip.createTrip
import io.andrelucas.business.trip.TripRepository
import io.andrelucas.repository.trip.TripDaoInMemory
import io.andrelucas.repository.trip.TripInMemoryRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.tripModule() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            logError(call, cause)
            call.respond(HttpStatusCode.InternalServerError, cause.message ?: "Unexpected error")
        }

        exception<IllegalArgumentException> { call, cause ->
            logError(call, cause)
            call.respond(HttpStatusCode.UnprocessableEntity, cause.message ?: "Error creating trip")
        }
    }

    routing {
        val tripRepository: TripRepository = TripInMemoryRepository()
        createTrip(TripService(tripRepository, TripDaoInMemory(tripRepository)))
        allTrips(TripService(tripRepository, TripDaoInMemory(tripRepository)))
    }
}