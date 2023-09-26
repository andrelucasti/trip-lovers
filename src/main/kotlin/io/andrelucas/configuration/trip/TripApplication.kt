package io.andrelucas.configuration.trip

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.tripModule() {
    routing {
        get("/trips/hello") {
            call.respond(HttpStatusCode.OK, "Hello trips")
        }
        // Create a trip
        post("{userId}/trips") {
            call.respond(HttpStatusCode.Created)
        }
        // All trips
        get("{userId}/trips/") {
            call.respond(HttpStatusCode.OK)
        }
    }
}