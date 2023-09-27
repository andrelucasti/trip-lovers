package io.andrelucas.business.trip

import java.time.LocalDate
import java.util.UUID

data class Trip(val id: UUID,
                val title: String,
                val destination: String,
                val about: String,
                val departure: LocalDate,
                val returns: LocalDate,
                val localities: List<Locality>,
                val accommodations: List<Accommodation>,
                val needsVisa: Boolean,
                val estTotalPrice: Int,
                val userId: UUID) {

    companion object {
        fun create(title: String,
                   destination: String,
                   about: String,
                   departure: LocalDate,
                   returns: LocalDate,
                   localities: List<Locality>,
                   accommodations: List<Accommodation>,
                   needsVisa: Boolean,
                   userId: UUID): Trip {

            val estTotalPrice =  localities.sumOf { it.estPrice }.plus(accommodations.sumOf { it.estPrice })
            return Trip(id = UUID.randomUUID(), title, destination, about, departure, returns, localities, accommodations, needsVisa, estTotalPrice, userId)
        }
    }
}