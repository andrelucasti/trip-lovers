package io.andrelucas.app.trip

import io.andrelucas.app.LocalityRequest
import io.andrelucas.app.TripRequest
import io.andrelucas.app.TripService
import io.andrelucas.business.trip.TripRepository
import io.andrelucas.repository.trip.TripInMemoryRepository
import kotlinx.coroutines.test.runTest
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TripServiceIntegrationTest {

    private lateinit var tripRepository: TripRepository
    private lateinit var tripService: TripService

    @BeforeTest
    fun setUp() {
        tripRepository = TripInMemoryRepository()
        tripService = TripService(tripRepository)
    }

    @Test
    fun shouldCreateATripWhenOnlyHaveLocalities() = runTest {
        // Given
        val tripRequest = TripRequest(
            title = "My trip to the moon",
            about = "I will go to the moon",
            departure = "2019-01-01",
            returns = "2019-01-10",
            localities = listOf(
                LocalityRequest(
                    name = "Moon",
                    latitude = 0.0,
                    longitude = 1.0,
                    type = "RESTAURANT"
                ),
            ),
            accommodations = emptyList(),
            needsVisa = false,
            userId = UUID.randomUUID()
        )
        // When
        tripService.create(tripRequest = tripRequest)
        // Then

        val trip = tripRepository.findAll().stream().findFirst().get()
        assertNotNull(trip.id)
        assertEquals(tripRequest.title, trip.title)
        assertEquals(tripRequest.about, trip.about)
        assertEquals(tripRequest.departure, trip.departure)
        assertEquals(tripRequest.returns, trip.returns)
        assertEquals(tripRequest.localities.size, trip.localities.size)
        assertEquals(tripRequest.accommodations.size, trip.accommodations.size)
        assertEquals(tripRequest.needsVisa, trip.needsVisa)
        assertEquals(tripRequest.userId, trip.userId)
        assertNotNull(trip.estTotalPrice)

        assertNotNull(trip.localities[0].id)
        assertEquals(tripRequest.localities[0].name, trip.localities[0].name)
        assertEquals(tripRequest.localities[0].latitude, trip.localities[0].latitude)
        assertEquals(tripRequest.localities[0].longitude, trip.localities[0].longitude)
        assertEquals(tripRequest.localities[0].type, trip.localities[0].localityType.name)
        assertNotNull(trip.localities[0].address)
        assertNotNull(trip.localities[0].estPrice)
    }
}