package io.andrelucas.business

import io.andrelucas.business.Coordinates

interface CoordinatesApi {

    fun fetchCoordinates(destination: String): Coordinates
}