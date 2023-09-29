package io.andrelucas.app.trip

import io.andrelucas.app.locality.LocalityRequest
import io.andrelucas.app.vehicle.VehicleRequest
import io.andrelucas.business.trip.TripDao
import io.andrelucas.business.trip.TripRepository
import io.andrelucas.repository.trip.TripDaoInMemory
import io.andrelucas.repository.trip.TripInMemoryRepository
import kotlinx.coroutines.test.runTest
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TripServiceIntegrationTest {

    private lateinit var tripRepository: TripRepository
    private lateinit var tripService: TripService
    private lateinit var tripDao: TripDao

    @BeforeTest
    fun setUp() {
        tripRepository = TripInMemoryRepository()
        tripDao = TripDaoInMemory(tripRepository)
        tripService = TripService(tripRepository, tripDao)
    }

    @Test
    fun shouldCreateATripWhenOnlyHaveLocalities() = runTest {
        // Given
        val tripRequest = TripRequest(
            title = "My trip to the moon",
            about = "I will go to the moon",
            destination = "Moon",
            departure = "2019-01-01",
            returns = "2019-01-10",
            localities = listOf(
                LocalityRequest(
                    name = "Moon",
                    latitude = 0.0,
                    longitude = 1.0,
                    type = "RESTAURANT",
                    estimatePriceInCents = 150
                ),
            ),
            accommodations = emptyList(),
            vehicles = listOf(
                VehicleRequest(
                    type = "CAR",
                    company = "Uber",
                    servicesOffering = listOf("Air conditioning", "Wi-Fi", "Food"),
                    estimatePriceInCents = 30000
                )
            ),
            needsVisa = false,
            adults = 1,
            userId = UUID.randomUUID().toString()

        )
        // When
        tripService.create(tripRequest = tripRequest)
        // Then

        val trip = tripRepository.findAll().stream().findFirst().get()
        assertNotNull(trip.id)
        assertEquals(tripRequest.title, trip.title)
        assertEquals(tripRequest.about, trip.about)
        assertEquals(tripRequest.destination, trip.destination)
        assertEquals(tripRequest.departure, trip.departure.format(DateTimeFormatter.ISO_LOCAL_DATE))
        assertEquals(tripRequest.returns, trip.returns.format(DateTimeFormatter.ISO_LOCAL_DATE))
        assertEquals(tripRequest.localities.size, trip.localities.size)
        assertEquals(tripRequest.accommodations.size, trip.accommodations.size)
        assertEquals(tripRequest.vehicles.size, trip.vehicles.size)
        assertEquals(tripRequest.needsVisa, trip.needsVisa)
        assertEquals(UUID.fromString(tripRequest.userId), trip.userId)
        assertNotNull(trip.estTotalPrice)

        assertNotNull(trip.localities[0].id)
        assertEquals(tripRequest.localities[0].name, trip.localities[0].name)
        assertEquals(tripRequest.localities[0].latitude, trip.localities[0].coordinates.latitude)
        assertEquals(tripRequest.localities[0].longitude, trip.localities[0].coordinates.longitude)
        assertEquals(tripRequest.localities[0].type, trip.localities[0].localityType.name)
        assertEquals(tripRequest.localities[0].estimatePriceInCents, trip.localities[0].estPrice.valueInDollarInCents())

        assertEquals(tripRequest.vehicles[0].type, trip.vehicles[0].vehicleType.name)
        assertEquals(tripRequest.vehicles[0].company, trip.vehicles[0].company)
        assertEquals(tripRequest.vehicles[0].servicesOffering, trip.vehicles[0].servicesOffering)
        assertEquals(tripRequest.vehicles[0].estimatePriceInCents, trip.vehicles[0].estimatePrice.valueInDollarInCents())

        assertNotNull(trip.localities[0].address)
        assertNotNull(trip.localities[0].estPrice)
    }
    @Test
    fun shouldReturnOnlyFutureTripsWhenTheDepartureIsAfterCurrentDate() = runTest  {
        val currentDate = LocalDate.parse("2023-01-01")
        val userId = UUID.randomUUID().toString()

        val tripRequest1 = TripRequest(
            title = "My trip to the moon",
            about = "I will go to the moon",
            destination = "Moon",
            departure = "2023-04-01",
            returns = "2023-04-08",
            localities = listOf(
                LocalityRequest(
                    name = "Moon",
                    latitude = 0.0,
                    longitude = 1.0,
                    type = "TOURIST_SPOT",
                    estimatePriceInCents = 150
                ),
            ),
            accommodations = emptyList(),
            vehicles = listOf(
                VehicleRequest(
                    type = "AIRPLANE",
                    company = "Uber",
                    servicesOffering = listOf("Air conditioning", "Wi-Fi", "Food"),
                    estimatePriceInCents = 30000
                )
            ),
            needsVisa = false,
            adults = 1,
            userId = userId
        )
        tripService.create(tripRequest1)

        val tripRequest2 = TripRequest(
            title = "My trip to the mars",
            about = "I will go to the mars",
            destination = "Mars",
            departure = "2023-05-01",
            returns = "2023-05-08",
            localities = listOf(
                LocalityRequest(
                    name = "Moon",
                    latitude = 0.0,
                    longitude = 1.0,
                    type = "TOURIST_SPOT",

                    estimatePriceInCents = 150
                ),
            ),
            accommodations = emptyList(),
            vehicles = listOf(
                VehicleRequest(
                    type = "AIRPLANE",
                    company = "Uber",
                    servicesOffering = listOf("Air conditioning", "Wi-Fi", "Food"),
                    estimatePriceInCents = 30000
                )
            ),
            needsVisa = false,
            adults = 1,
            userId = userId
        )
        tripService.create(tripRequest2)

        val tripRequest3 = TripRequest(
            title = "My trip to the earth",
            about = "I will go to the earth",
            destination = "Earth",
            departure = "2022-05-01",
            returns = "2022-05-08",
            localities = listOf(
                LocalityRequest(
                    name = "Moon",
                    latitude = 0.0,
                    longitude = 1.0,
                    type = "TOURIST_SPOT",
                    estimatePriceInCents = 150
                ),
            ),
            accommodations = emptyList(),
            vehicles = listOf(
                VehicleRequest(
                    type = "AIRPLANE",
                    company = "Uber",
                    servicesOffering = listOf("Air conditioning", "Wi-Fi", "Food"),
                    estimatePriceInCents = 30000
                )
            ),
            needsVisa = false,
            adults = 1,
            userId = userId
        )
        tripService.create(tripRequest3)

        val futureTrips = tripService.findAllFutureTrips(currentDate, UUID.fromString(userId))

        assertEquals(2, futureTrips.size)
        assertEquals(tripRequest2.title, futureTrips[0].title)
        assertEquals(tripRequest1.title, futureTrips[1].title)
    }

    fun shouldReturnOnlyOldTripsWhenTheDepartureAndReturnIsBeforeCurrentDate() {
        // Given
        // When
        // Then
    }
}