package io.andrelucas.app.tripplanning

import io.andrelucas.business.Coordinates

data class TripPlanningResponse(val title: String,
                                val destination: String,
                                val coordinates: Coordinates) {
}