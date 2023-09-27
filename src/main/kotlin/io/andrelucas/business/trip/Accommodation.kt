package io.andrelucas.business.trip

import java.util.UUID

data class Accommodation(val id: UUID,
                         val name: String,
                         val accommodationType: AccommodationType,
                         val latitude: Double,
                         val longitude: Double,
                         val address : String,
                         val estPrice: Int,
                         val nightsAmount: Int,
                         val servicesOffering: List<String>) {
    companion object {
        fun create(name: String,
                   accommodationType: AccommodationType,
                   latitude: Double,
                   longitude: Double,
                   address : String,
                   estPrice: Int,
                   servicesOffering: List<String>): Accommodation {

            return Accommodation(id = UUID.randomUUID(), name, accommodationType, latitude, longitude, address, estPrice, 0, servicesOffering)
        }
    }
}

enum class AccommodationType {
    HOTEL, MOTEL, HOSTEL, AIRBNB, BED_AND_BREAKFAST, BOAT, BEACH_HOUSE, BEDROOM, OTHER
}
