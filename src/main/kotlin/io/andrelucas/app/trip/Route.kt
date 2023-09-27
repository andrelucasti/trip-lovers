package io.andrelucas.app.trip

import io.ktor.server.routing.*

fun Route.createTrip(tripService: TripService){
    post("/trips") {
        //TODO: call tripService.create()
    }
}