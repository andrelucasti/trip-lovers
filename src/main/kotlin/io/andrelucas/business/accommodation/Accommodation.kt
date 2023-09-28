package io.andrelucas.business.accommodation

import io.andrelucas.business.Coordinates
import io.andrelucas.business.Price
import java.util.UUID

data class Accommodation(val id: UUID,
                         val name: String,
                         val accommodationType: AccommodationType,
                         val coordinates: Coordinates,
                         val address : String,
                         val estPrice: Price,
                         val nightsAmount: Int,
                         val servicesOffering: List<String>) {
    companion object {
        fun create(name: String,
                   accommodationType: AccommodationType,
                   coordinates: Coordinates,
                   address : String,
                   estPrice: Price,
                   servicesOffering: List<String>): Accommodation {

            return Accommodation(id = UUID.randomUUID(), name, accommodationType, coordinates, address, estPrice, 0, servicesOffering)
        }
    }
}

enum class AccommodationType {
    HOTEL, MOTEL, HOSTEL, AIRBNB, BED_AND_BREAKFAST, BOAT, BEACH_HOUSE, BEDROOM, OTHER
}
