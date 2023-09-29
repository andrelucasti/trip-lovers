package io.andrelucas.business.trip

import io.andrelucas.business.Currency
import io.andrelucas.business.Price
import io.andrelucas.business.accommodation.Accommodation
import io.andrelucas.business.locality.Locality
import io.andrelucas.business.vehicle.Vehicle
import java.time.LocalDate
import java.util.*

data class Trip(val id: UUID,
                val title: String,
                val destination: String,
                val about: String,
                val departure: LocalDate,
                val returns: LocalDate,
                val localities: List<Locality>,
                val accommodations: List<Accommodation>,
                val vehicles: List<Vehicle>,
                val needsVisa: Boolean,
                val adults: Int,
                val estTotalPrice: Price,
                val userId: UUID) {

    companion object {
        fun create(title: String,
                   destination: String,
                   about: String,
                   departure: LocalDate,
                   returns: LocalDate,
                   localities: List<Locality>,
                   accommodations: List<Accommodation>,
                   vehicles: List<Vehicle>,
                   needsVisa: Boolean,
                   adults: Int,
                   userId: UUID): Trip {

            if (vehicles.isEmpty()) throw IllegalArgumentException("Should not create a trip without vehicle")

            if (localities.isEmpty() && accommodations.isEmpty()) throw IllegalArgumentException("Should not create a trip without localities and accommodations")

            val estTotalPrice =  localities.sumOf { it.estPrice.valueInDollarInCents() }
                .plus(accommodations.sumOf { it.estPrice.valueInDollarInCents() })
                .plus(vehicles.sumOf { it.estimatePrice.valueInDollarInCents() })

            return Trip(id = UUID.randomUUID(), title, destination, about, departure, returns, localities, accommodations, vehicles, needsVisa, adults, Price(estTotalPrice, Currency.USD), userId)
        }
    }
}