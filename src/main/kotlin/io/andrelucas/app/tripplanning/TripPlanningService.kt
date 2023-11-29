package io.andrelucas.app.tripplanning

import io.andrelucas.business.CoordinatesApi

class TripPlanningService(private val coordinatesApi: CoordinatesApi) {

    fun planTrip(destination: String): TripPlanningResponse {
        val coordinates = coordinatesApi.fetchCoordinates(destination)


        return TripPlanningResponse(
            title = "Trip to $destination",
            destination = destination,
            coordinates = coordinates
        )
    }
}