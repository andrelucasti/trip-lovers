package io.andrelucas.business.trip

import java.util.UUID

data class Locality(val id: UUID,
                    val name: String,
                    val localityType: LocalityType,
                    val latitude: Double,
                    val longitude: Double,
                    val address : String,
                    val estPrice: Int,
                    val tripId: String) {
    companion object {
        fun create(name: String,
                   localityType: LocalityType,
                   latitude: Double,
                   longitude: Double,
                   address : String,
                   estPrice: Int,
                   tripId: String): Locality {

            return Locality(id = UUID.randomUUID(), name, localityType, latitude, longitude, address, estPrice, tripId)
        }
    }
}

enum class LocalityType {
    RESTAURANT, TOURIST_SPOT, SNACK_BAR, CLUB, BAR, OTHER
}


