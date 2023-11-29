package io.andrelucas.app.tripplanning

import io.andrelucas.business.Coordinates
import io.andrelucas.business.CoordinatesApi
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TripPlanningServiceIntegrationTest{

    fun shouldReturnATripPlannerWhenTheDestinationIsProvided() {
        // This planner should have the following characteristics:
        // - The places to visit
        // - The geolocation of the places to visit
        // - 3 hotels to stay
        // - The geolocation of destination

        // Given
        // When
        // Then
    }

    @Test
    fun shouldReturnGeolocationOfDestinationWhenTripIsPlanned(){
        // Given
        val coordinatesApi = mockk<CoordinatesApi>()
        val tripPlanningService = TripPlanningService(coordinatesApi)

        // When
        every { coordinatesApi.fetchCoordinates(any()) } returns Coordinates(0.0, 0.0)
        val plannerTrip = tripPlanningService.planTrip("Lisbon")

        // Then
        assertEquals("Lisbon", plannerTrip.destination)
        assertNotNull(plannerTrip.coordinates)
        assertEquals(0.0, plannerTrip.coordinates.latitude)
        assertEquals(0.0, plannerTrip.coordinates.longitude)
    }
}