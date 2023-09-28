package io.andrelucas.business.trip

import io.andrelucas.business.Coordinates
import io.andrelucas.business.Currency
import io.andrelucas.business.Price
import io.andrelucas.business.accommodation.Accommodation
import io.andrelucas.business.accommodation.AccommodationType
import io.andrelucas.business.locality.Locality
import io.andrelucas.business.locality.LocalityType
import java.time.LocalDate
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class TripTest {
    @Test
    fun shouldNotCreateATripWhenDoesNotHaveLocalitiesAndAccommodations() {
        assertFails("Should not create a trip without localities, accommodations and vehicles")
        { Trip.create(
            "My trip",
            "Amsterdam",
            "My description",
            LocalDate.parse("2019-01-01"),
            LocalDate.parse("2019-01-10"),
            emptyList(), emptyList(), false, 1, UUID.randomUUID())
        }
    }

    @Test
    fun shouldReturnTheSumOfTripEstimatedPriceWhenIsCreatedWithOnlyLocalities() {
        val nataFactory =
            Locality.create(
                "Fabrica de Nata", LocalityType.TOURIST_SPOT,
                Coordinates(0.0, 0.0), "address 1", Price(150, Currency.USD)
            )

        val trip1 = Trip.create(
            "My trip",
            "Lisbon",
            "My description",
            LocalDate.parse("2019-01-01"),
            LocalDate.parse("2019-01-10"),
            listOf(nataFactory), emptyList(), false, 1, UUID.randomUUID()
        )

        assertEquals(150, trip1.estTotalPrice.valueInDollarInCents())

        val brenanCastel = Locality.create(
            "Castelo de Brenan", LocalityType.TOURIST_SPOT,
            Coordinates(0.0, 0.0), "address 1", Price(100, Currency.USD)
        )
        val chinaBox = Locality.create(
            "China Box", LocalityType.RESTAURANT,
            Coordinates(0.0, 0.0), "address 1", Price(250, Currency.USD)
        )

        val trip2 = Trip.create(
            "My trip",
            "Recife",
            "My description",
            LocalDate.parse("2019-01-01"),
            LocalDate.parse("2019-01-10"),
            listOf(brenanCastel, chinaBox), emptyList(), false, 1, UUID.randomUUID()
        )

        assertEquals(350, trip2.estTotalPrice.valueInDollarInCents())
    }

    @Test
    fun shouldReturnTheSumOfTripEstimatedPriceWhenIsCreatedWithOnlyAccommodations() {
        val ibisHotel = Accommodation.create("Ibis Hotel", AccommodationType.HOTEL, Coordinates(0.0, 0.0), "address 1", Price(10000, Currency.USD), emptyList())

        val trip1 = Trip.create(
            "My trip",
            "Spain",
            "My description",
            LocalDate.parse("2019-01-01"),
            LocalDate.parse("2019-01-10"),
            emptyList(), listOf(ibisHotel), false, 1, UUID.randomUUID()
        )

        assertEquals(10000, trip1.estTotalPrice.valueInDollarInCents())

        val hostel1 = Accommodation.create("Cool Hostel", AccommodationType.HOSTEL, Coordinates(0.0, 0.0), "address 1", Price(5000, Currency.USD), emptyList())
        val hostel2 = Accommodation.create("Kids Hostel", AccommodationType.HOSTEL, Coordinates(0.0, 0.0), "address 1", Price(3000, Currency.USD), emptyList())

        val trip2 = Trip.create(
            "My trip",
            "Portugal",
            "My description",
            LocalDate.parse("2019-01-01"),
            LocalDate.parse("2019-01-10"),
            emptyList(), listOf(hostel1, hostel2), false, 1, UUID.randomUUID()
        )

        assertEquals(8000, trip2.estTotalPrice.valueInDollarInCents())
    }

    @Test
    fun shouldReturnTheSumOfTripEstimatedPriceWhenIsCreatedWithLocalitiesAndAccommodations() {
        val ibisHotel = Accommodation.create("Ibis Hotel", AccommodationType.HOTEL, Coordinates(0.0, 0.0), "address 1", Price(30000, Currency.USD), emptyList())
        val heineken = Locality.create(
            "Heineken Experience",
            LocalityType.TOURIST_SPOT,
            Coordinates(0.0, 0.0),
            "address 1",
            Price(3000, Currency.USD)
        )
        val museum = Locality.create(
            "Anna Frank",
            LocalityType.MUSEUM,
            Coordinates(0.0, 0.0),
            "address 1",
            Price(1500, Currency.USD)
        )

        val trip = Trip.create(
            "My trip",
            "Amsterdam",
            "My description",
            LocalDate.parse("2019-01-01"),
            LocalDate.parse("2019-01-10"),
            listOf(heineken, museum), listOf(ibisHotel), false, 1, UUID.randomUUID()
        )

        assertEquals(34500, trip.estTotalPrice.valueInDollarInCents())
    }

    @Test
    fun shouldReturnTheSumOfTripEstimatedPriceWhenIsCreatedWithMoreThenOneAdults(){
        val ibisHotel = Accommodation.create("Ibis Hotel", AccommodationType.HOTEL, Coordinates(0.0, 0.0), "address 1", Price(30000, Currency.USD), emptyList())
        val heineken = Locality.create(
            "Heineken Experience",
            LocalityType.TOURIST_SPOT,
            Coordinates(0.0, 0.0),
            "address 1",
            Price(3000, Currency.USD)
        )
        val museum = Locality.create(
            "Anna Frank",
            LocalityType.MUSEUM,
            Coordinates(0.0, 0.0),
            "address 1",
            Price(1500, Currency.USD)
        )

        val trip = Trip.create(
            "My trip",
            "Amsterdam",
            "My description",
            LocalDate.parse("2019-01-01"),
            LocalDate.parse("2019-01-10"),
            listOf(heineken, museum), listOf(ibisHotel), false, 2, UUID.randomUUID()
        )

        assertEquals(39000, trip.estTotalPrice.valueInDollarInCents())
    }
}