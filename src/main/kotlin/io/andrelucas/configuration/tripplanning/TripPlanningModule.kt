package io.andrelucas.configuration.tripplanning

import io.andrelucas.app.tripplanning.TripPlanningService
import io.andrelucas.thirdparty.CoordinatesApiImpl
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.tripPlanningModule() {
    routing {
        val tripPlanningService = TripPlanningService(CoordinatesApiImpl())
        planTrip(tripPlanningService)

    }
}

fun Route.planTrip(tripPlanningService: TripPlanningService) {
    route("/trip-planning") {
        get {
            val destination = call.parameters["destination"] ?: throw IllegalArgumentException("Destination is required")
            tripPlanningService.planTrip(destination).let { trips ->
                call.respond(trips)
            }
        }
    }
}