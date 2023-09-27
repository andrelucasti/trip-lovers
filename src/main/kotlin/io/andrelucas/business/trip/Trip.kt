package io.andrelucas.business.trip

import java.util.UUID

data class Trip(val id: UUID,
                val title: String,
                val about: String,
                val departure: String,
                val returns: String,
                val localities: List<Locality>,
                val accommodations: List<Accommodation>,
                val needsVisa: Boolean,
                val estTotalPrice: Int,
                val userId: UUID) {
}