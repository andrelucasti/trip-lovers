package io.andrelucas.business.trip

import java.util.UUID

data class Accommodation(val id: UUID,
                         val name: String,
                         val accommodationType: AccommodationType,
                         val latitude: Double,
                         val longitude: Double,
                         val address : String,
                         val estPrice: Int,
                         val servicesOffering: List<String>,
                         val tripId: String) {
    companion object {
        fun create(name: String,
                   accommodationType: AccommodationType,
                   latitude: Double,
                   longitude: Double,
                   address : String,
                   estPrice: Int,
                   servicesOffering: List<String>,
                   tripId: String): Accommodation {

            return Accommodation(id = UUID.randomUUID(), name, accommodationType, latitude, longitude, address, estPrice, servicesOffering, tripId)
        }
    }
}

enum class AccommodationType {
    HOTEL, MOTEL, HOSTEL, AIRBNB, BED_AND_BREAKFAST, BOAT, BEACH_HOUSE, BEDROOM, OTHER
}
