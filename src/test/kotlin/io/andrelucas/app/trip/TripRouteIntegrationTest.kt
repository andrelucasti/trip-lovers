package io.andrelucas.app.trip

import io.andrelucas.configuration.app.appModule
import io.andrelucas.configuration.trip.tripModule
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TripRouteIntegrationTest {
    @Test
    fun shouldReturn422WhenIsTriedCreatingATripWithAccommodationsAndLocalitiesEmpty() = testApplication {
        application {
            appModule()
            tripModule()
        }

        client.post("/trips") {
            contentType(ContentType.Application.Json)
            setBody(
                """
                {
                    "title":"My Trip",
                    "about":"About my trip",
                    "destination": "Roma",
                    "departure": "2024-04-01",
                    "returns": "2024-04-09",
                    "localities":[],
                    "accommodations":[],
                    "needsVisa":false,
                    "adults": 1,
                    "userId":"c1ae16dd-f875-442f-a123-ec467ec0a52a"
                }
                """.trimIndent()
            )
        }.apply {
            assertEquals(HttpStatusCode.UnprocessableEntity, status)
        }
    }


    @Test
    fun shouldCreateATripWhenOnlyHaveAccomodations() = testApplication {
        application {
            appModule()
            tripModule()
        }

        client.post("/trips") {
            contentType(ContentType.Application.Json)
            setBody(
                """
                {
                    "title":"My Trip",
                    "about":"About my trip",
                    "destination": "Roma",
                    "departure": "2024-04-01",
                    "returns": "2024-04-09",
                    "localities":[],
                    "accommodations":[{
                        "name":"Ibis Hotel",
                        "type": "HOTEL",
                        "latitude": 41.84660739499335,
                        "longitude": 12.592853311316297,
                        "estimatePriceInCents":"8000",
                        "servicesOffering":["wifi", "2 beds", "breakfast", "clean"]
                    }],
                    "needsVisa":false,
                    "adults": 2,
                    "userId":"c1ae16dd-f875-442f-a123-ec467ec0a52a"
                
                }
                                """.trimIndent()
            )
        }.apply {
            assertEquals(HttpStatusCode.Created, status)
        }

        client.get("/trips").apply {
            assertEquals(HttpStatusCode.OK, status)

            println(bodyAsText())
        }


    }

    fun shouldCreateATripWhenOnlyHaveVehicles() {
        // Given
        // When
        // Then
    }
}