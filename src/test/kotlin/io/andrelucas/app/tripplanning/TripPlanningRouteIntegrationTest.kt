package io.andrelucas.app.tripplanning

import io.andrelucas.configuration.app.appModule
import io.andrelucas.configuration.trip.tripModule
import io.andrelucas.configuration.tripplanning.tripPlanningModule
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class TripPlanningRouteIntegrationTest {

    @Test
    fun shouldPlanTrip() = testApplication{

        // Given
        application {
            appModule()
            tripPlanningModule()
        }

        client.get("/trip-planning?destination=Lisbon"){
            contentType(ContentType.Application.Json)

        }.apply {
            // Then
            assertEquals(HttpStatusCode.OK, status)
            val tripPlanningResponse = Json.decodeFromString<TripPlanningResponse>(bodyAsText())
            assertEquals("Lisbon", tripPlanningResponse.destination)
            assertNotNull(tripPlanningResponse.coordinates)
            assertNotEquals(0.0, tripPlanningResponse.coordinates.latitude)
            assertNotEquals(0.0, tripPlanningResponse.coordinates.longitude)
        }
    }
}