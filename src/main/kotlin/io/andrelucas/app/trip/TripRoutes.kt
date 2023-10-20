package io.andrelucas.app.trip

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createTrip(tripService: TripService){
    route("/trips"){
        post {
            val tripRequest = call.receive<TripRequest>()
            tripService.create(tripRequest).let {
                call.respond(HttpStatusCode.Created, "Trip created")
            }
        }
    }
}

fun Route.allTrips(tripService: TripService){
    route("/trips"){
        get {
            tripService.findAll().let { trips ->
                call.respond(HttpStatusCode.OK, trips)
            }
        }
    }
}