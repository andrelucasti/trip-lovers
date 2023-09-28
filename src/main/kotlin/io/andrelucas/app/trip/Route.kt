package io.andrelucas.app.trip

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createTrip(tripService: TripService){
    route("/trips"){
        post {
            val tripRequest = call.receive<TripRequest>()
            try {
                tripService.create(tripRequest)
                call.respond(HttpStatusCode.Created, "Trip created")
            } catch (e: Exception) {
                logError(call, e)
                call.respond(HttpStatusCode.UnprocessableEntity, e.message ?: "Error creating trip")
            }
        }

        get {
            tripService.findAll().let { trips ->
                call.respond(HttpStatusCode.OK, trips)
            }
        }
    }
}