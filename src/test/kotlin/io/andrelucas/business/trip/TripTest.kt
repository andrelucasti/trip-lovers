package io.andrelucas.business.trip

import java.time.LocalDate
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TripTest {

    fun shouldNotCreateATripWhenDoesNotHaveLocalitiesAndAccommodationsAndVehicles() {
        // Given
        // When
        // Then
    }

    fun shouldReturnOnlyFutureTripsWhenTheDepartureIsAfterCurrentDate() {
        // Given
        // When
        // Then
    }

    fun shouldReturnOnlyOldTripsWhenTheDepartureAndReturnIsBeforeCurrentDate() {
        // Given
        // When
        // Then
    }

    @Test
    fun shouldReturnTheSumOfTripEstimatedPriceWhenIsCreatedWithOnlyLocalities() {
        val nataFactory = Locality.create("Fabrica de Nata", LocalityType.TOURIST_SPOT, 0.0, 0.0, "address 1 ", 150)

        val trip1 = Trip.create(
            "My trip",
            "Lisbon",
            "My description",
            LocalDate.parse("2019-01-01"),
            LocalDate.parse("2019-01-10"),
            listOf(nataFactory), emptyList(), false, UUID.randomUUID()
        )

        assertEquals(150, trip1.estTotalPrice)

        val brenanCastel = Locality.create("Castelo de Brenan", LocalityType.TOURIST_SPOT, 0.0, 0.0, "address 1 ", 100)
        val chinaBox = Locality.create("China Box", LocalityType.RESTAURANT, 0.0, 0.0, "adress 2", 250)

        val trip2 = Trip.create(
            "My trip",
            "Recife",
            "My description",
            LocalDate.parse("2019-01-01"),
            LocalDate.parse("2019-01-10"),
            listOf(brenanCastel, chinaBox), emptyList(), false, UUID.randomUUID()
        )

        assertEquals(350, trip2.estTotalPrice)
    }

    @Test
    fun shouldReturnTheSumOfTripEstimatedPriceWhenIsCreatedWithOnlyAccommodations() {
        val ibisHotel = Accommodation.create("Ibis Hotel", AccommodationType.HOTEL, 0.0, 0.0, "address 1 ", 10000, emptyList())

        val trip1 = Trip.create(
            "My trip",
            "Spain",
            "My description",
            LocalDate.parse("2019-01-01"),
            LocalDate.parse("2019-01-10"),
            emptyList(), listOf(ibisHotel), false, UUID.randomUUID()
        )

        assertEquals(10000, trip1.estTotalPrice)

        val hostel1 = Accommodation.create("Cool Hostel", AccommodationType.HOSTEL, 0.0, 0.0, "address 1 ", 5000, emptyList())
        val hostel2 = Accommodation.create("Kids Hostel", AccommodationType.HOSTEL, 0.0, 0.0, "address 1 ", 3000, emptyList())

        val trip2 = Trip.create(
            "My trip",
            "Portugal",
            "My description",
            LocalDate.parse("2019-01-01"),
            LocalDate.parse("2019-01-10"),
            emptyList(), listOf(hostel1, hostel2), false, UUID.randomUUID()
        )

        assertEquals(8000, trip2.estTotalPrice)
    }

    @Test
    fun shouldReturnTheSumOfTripEstimatedPriceWhenIsCreatedWithLocalitiesAndAccommodations() {
        val ibisHotel = Accommodation.create("Ibis Hotel", AccommodationType.HOTEL, 0.0, 0.0, "address 1 ", 30000, emptyList())

        val heineken = Locality.create("Heineken Experience", LocalityType.TOURIST_SPOT, 0.0, 0.0, "address 1 ", 3000)
        val museum = Locality.create("Anna Frank", LocalityType.MUSEUM, 0.0, 0.0, "address 1 ", 1500)

        val trip = Trip.create(
            "My trip",
            "Amsterdam",
            "My description",
            LocalDate.parse("2019-01-01"),
            LocalDate.parse("2019-01-10"),
            listOf(heineken, museum), listOf(ibisHotel), false, UUID.randomUUID()
        )

        assertEquals(34500, trip.estTotalPrice)
    }
}