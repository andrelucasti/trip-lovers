package io.andrelucas.business.locality

import io.andrelucas.business.Coordinates
import io.andrelucas.business.Price
import java.util.UUID

data class Locality(
    val id: UUID,
    val name: String,
    val localityType: LocalityType,
    val coordinates: Coordinates,
    val address: String,
    val estPrice: Price
) {
    companion object {
        fun create(
            name: String,
            localityType: LocalityType,
            coordinates: Coordinates,
            address: String,
            estPrice: Price
        ): Locality {

            return Locality(id = UUID.randomUUID(), name, localityType, coordinates, address, estPrice)
        }
    }
}

enum class LocalityType {
    RESTAURANT, TOURIST_SPOT, SNACK_BAR, CLUB, BAR, MUSEUM, TOUR, OTHER
}


