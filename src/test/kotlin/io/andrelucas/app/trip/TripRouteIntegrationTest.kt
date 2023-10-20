package io.andrelucas.app.trip

import io.andrelucas.configuration.app.appModule
import io.andrelucas.configuration.trip.tripModule
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
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
                    "vehicles":[
                            {
                                "company":"Uber",
                                "type":"AIRPLANE",
                                "estimatePriceInCents":30000,
                                "servicesOffering":["wifi", "breakfast"]
                            }
                        ],
                    "needsVisa":false,
                    "adults": 1,
                    "userId":"c1ae16dd-f875-442f-a123-ec467ec0a52a"
                }
                """.trimIndent()
            )
        }.apply {
            assertEquals(HttpStatusCode.UnprocessableEntity, status)
            assertEquals("Should not create a trip without localities and accommodations", bodyAsText())
        }
    }

    @Test
    fun shouldReturn422WhenIsTriedCreatingATripWithVehicle() = testApplication {
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
                    "localities":[{
                        "name":"Ocenário Lisboa",
                        "type": "TOURIST_SPOT",
                        "latitude": 38.7651130934309,
                        "longitude": -9.093708960777708,
                        "estimatePriceInCents":"3000"
                    }, {
                        "name":"Ocenário Lisboa",
                        "type": "RESTAURANT",
                        "latitude": 38.71820198239517,
                        "longitude": -9.142133168164838,
                        "estimatePriceInCents":"1000"
                    }],
                    "accommodations":[{
                        "name":"ibis hotel",
                        "type": "HOTEL",
                        "latitude": 41.84660739499335,
                        "longitude": 12.592853311316297,
                        "estimatePriceInCents":"8000",
                        "servicesOffering":["wifi", "2 beds", "breakfast", "clean"]
                    }],
                    "vehicles":[],
                    "needsVisa":false,
                    "adults": 1,
                    "userId":"c1ae16dd-f875-442f-a123-ec467ec0a52a"
                }
                """.trimIndent()
            )
        }.apply {
            assertEquals(HttpStatusCode.UnprocessableEntity, status)
            assertEquals("Should not create a trip without vehicle", bodyAsText())
        }
    }

    @Test
    fun shouldCreateATripWhenOnlyHaveAccommodations() = testApplication {
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
                    "vehicles":[
                        {
                            "company":"Uber",
                            "type":"AIRPLANE",
                            "estimatePriceInCents":30000,
                            "servicesOffering":["wifi", "breakfast"]
                        }
                    ],
                    "needsVisa":false,
                    "adults": 2,
                    "userId":"c1ae16dd-f875-442f-a123-ec467ec0a52a"
                
                }
                                """.trimIndent()
            )
        }.apply {
            assertEquals(HttpStatusCode.Created, status)
        }

        client.get("/trips"){
            contentType(ContentType.Application.Json)
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val tripResponse = Json.decodeFromString<List<TripResponse>>(bodyAsText())
                .first()

            assertEquals("My Trip", tripResponse.title)
            assertEquals("About my trip", tripResponse.about)
            assertEquals("Roma", tripResponse.destination)
            assertEquals("2024-04-01", tripResponse.departure)
            assertEquals("2024-04-09", tripResponse.returns)
            assertEquals(0, tripResponse.localities.size)
            assertEquals(2, tripResponse.adults)

            assertEquals(1, tripResponse.accommodations.size)
            assertEquals(68000, tripResponse.estimateTotalPrice)


        }
    }
    @Test
    fun shouldCreateATripWhenOnlyHaveLocalities() = testApplication {
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
                	"destination": "Lisbon",
                	"departure": "2024-04-01",
                	"returns": "2024-04-09",
                	"localities":[{
                		"name":"Ocenário Lisboa",
                		"type": "TOURIST_SPOT",
                		"latitude": 38.7651130934309,
                		"longitude": -9.093708960777708,
                		"estimatePriceInCents":"2500"
                	}, {
                		"name":"Fabrica de Nata",
                		"type": "RESTAURANT",
                		"latitude": 38.71820198239517,
                		"longitude": -9.142133168164838,
                		"estimatePriceInCents":"1000"
                	}]
                	"accommodations":[],
                    "vehicles":[
                        {
                            "company":"Uber",
                            "type":"AIRPLANE",
                            "estimatePriceInCents":30000,
                            "servicesOffering":["wifi", "breakfast"]
                        }
                    ],
                	"needsVisa":false,
                	"adults": 2,
                	"userId":"c1ae16dd-f875-442f-a123-ec467ec0a52a"

                }
                                """.trimIndent()
            )
        }.apply {
            assertEquals(HttpStatusCode.Created, status)
        }

        client.get("/trips"){
            contentType(ContentType.Application.Json)
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val tripResponse = Json.decodeFromString<List<TripResponse>>(bodyAsText())
                .first()
            assertEquals("My Trip", tripResponse.title)
            assertEquals("About my trip", tripResponse.about)
            assertEquals("Lisbon", tripResponse.destination)
            assertEquals("2024-04-01", tripResponse.departure)
            assertEquals("2024-04-09", tripResponse.returns)
            assertEquals(2, tripResponse.localities.size)
            assertEquals(2, tripResponse.adults)
            assertEquals(0, tripResponse.accommodations.size)

            assertEquals(63500, tripResponse.estimateTotalPrice)
            assertEquals(0, tripResponse.accommodations.size)
        }
    }

    @Test
    fun shouldReturnTheLastsTrips() = testApplication{
        application {
            appModule()
            tripModule()
        }

        client.post("/trips"){
            contentType(ContentType.Application.Json)
            setBody("""{
                        "title":"My Trip of the year",
                        "about":"About my trip of the year",
                        "destination": "Lisbon",
                        "departure": "2022-04-01",
                        "returns": "2022-04-09",
                        "localities":
                        [
                            {
                                "name":"Ocenário Lisboa",
                                "type": "TOURIST_SPOT",
                                "latitude": 38.7651130934309,
                                "longitude": -9.093708960777708,
                                "estimatePriceInCents":"3000"
                            }, 
                            {
                                "name":"Chimaraum",
                                "type": "RESTAURANT",
                                "latitude": 38.71820198239517,
                                "longitude": -9.142133168164838,
                                "estimatePriceInCents":"2700"
                            },
                            {
                                "name":"Bondinho Tour",
                                "type": "TOUR",
                                "latitude": 38.71820198239517,
                                "longitude": -9.142133168164838,
                                "estimatePriceInCents":"2000"
                            }
                        ],
                        "accommodations":[
                            {
                                "name":"ibis hotel",
                                "type": "HOTEL",
                                "latitude": 41.84660739499335,
                                "longitude": 12.592853311316297,
                                "estimatePriceInCents":"8000",
                                "servicesOffering":["wifi", "2 beds", "breakfast", "clean"]
                            }
                        ],
                    "vehicles":[
                        {
                            "company":"Uber",
                            "type":"AIRPLANE",
                            "estimatePriceInCents":30000,
                            "servicesOffering":["wifi", "breakfast"]
                        }
                    ],
                    "needsVisa":false,
                    "adults": 2,
                    "userId":"c1ae16dd-f875-442f-a123-ec467ec0a52a"
                }""")
        }.apply {
            assertEquals(HttpStatusCode.Created, status)
        }

        client.post("/trips"){
            contentType(ContentType.Application.Json)
            setBody("""{
                        "title":"My Trip of the year",
                        "about":"About my trip of the year",
                        "destination": "Lisbon",
                        "departure": "2022-04-01",
                        "returns": "2022-04-09",
                        "localities":
                        [
                            {
                                "name":"Ocenário Lisboa",
                                "type": "TOURIST_SPOT",
                                "latitude": 38.7651130934309,
                                "longitude": -9.093708960777708,
                                "estimatePriceInCents":"3000"
                            }, 
                            {
                                "name":"Chimaraum",
                                "type": "RESTAURANT",
                                "latitude": 38.71820198239517,
                                "longitude": -9.142133168164838,
                                "estimatePriceInCents":"2700"
                            },
                            {
                                "name":"Bondinho Tour",
                                "type": "TOUR",
                                "latitude": 38.71820198239517,
                                "longitude": -9.142133168164838,
                                "estimatePriceInCents":"2000"
                            }
                        ],
                        "accommodations":[
                            {
                                "name":"ibis hotel",
                                "type": "HOTEL",
                                "latitude": 41.84660739499335,
                                "longitude": 12.592853311316297,
                                "estimatePriceInCents":"8000",
                                "servicesOffering":["wifi", "2 beds", "breakfast", "clean"]
                            }
                        ],
                    "vehicles":[
                        {
                            "company":"Uber",
                            "type":"AIRPLANE",
                            "estimatePriceInCents":30000,
                            "servicesOffering":["wifi", "breakfast"]
                        }
                    ],
                    "needsVisa":false,
                    "adults": 2,
                    "userId":"c1ae16dd-f875-442f-a123-ec467ec0a52a"
                }""")
        }.apply {
            assertEquals(HttpStatusCode.Created, status)
        }

        client.get("/trips/lasts") {
            contentType(ContentType.Application.Json)
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val trips = Json.decodeFromString<List<TripResponse>>(bodyAsText())
            assertEquals(2, trips.size)
        }
    }

    @Test
    fun shouldReturnTripsByDestination() = testApplication {
        application {
            appModule()
            tripModule()
        }

        client.post("/trips"){
            contentType(ContentType.Application.Json)
            setBody("""{
                        "title":"My Trip of the year",
                        "about":"About my trip of the year",
                        "destination": "Lisbon",
                        "departure": "2022-04-01",
                        "returns": "2022-04-09",
                        "localities":
                        [
                            {
                                "name":"Ocenário Lisboa",
                                "type": "TOURIST_SPOT",
                                "latitude": 38.7651130934309,
                                "longitude": -9.093708960777708,
                                "estimatePriceInCents":"3000"
                            }, 
                            {
                                "name":"Chimaraum",
                                "type": "RESTAURANT",
                                "latitude": 38.71820198239517,
                                "longitude": -9.142133168164838,
                                "estimatePriceInCents":"2700"
                            },
                            {
                                "name":"Bondinho Tour",
                                "type": "TOUR",
                                "latitude": 38.71820198239517,
                                "longitude": -9.142133168164838,
                                "estimatePriceInCents":"2000"
                            }
                        ],
                        "accommodations":[
                            {
                                "name":"ibis hotel",
                                "type": "HOTEL",
                                "latitude": 41.84660739499335,
                                "longitude": 12.592853311316297,
                                "estimatePriceInCents":"8000",
                                "servicesOffering":["wifi", "2 beds", "breakfast", "clean"]
                            }
                        ],
                    "vehicles":[
                        {
                            "company":"Uber",
                            "type":"AIRPLANE",
                            "estimatePriceInCents":30000,
                            "servicesOffering":["wifi", "breakfast"]
                        }
                    ],
                    "needsVisa":false,
                    "adults": 2,
                    "userId":"c1ae16dd-f875-442f-a123-ec467ec0a52a"
                }""")
        }.apply {
            assertEquals(HttpStatusCode.Created, status)
        }

        client.post("/trips"){
            contentType(ContentType.Application.Json)
            setBody("""{
                        "title":"Romantic Trip",
                        "about":"About my trip to italy",
                        "destination": "Roma",
                        "departure": "2022-04-01",
                        "returns": "2022-04-09",
                        "localities":
                        [
                            {
                                "name":"Torre de Pisa",
                                "type": "TOURIST_SPOT",
                                "latitude": 38.7651130934309,
                                "longitude": -9.093708960777708,
                                "estimatePriceInCents":"3000"
                            }, 
                            {
                                "name":"Chimaraum",
                                "type": "RESTAURANT",
                                "latitude": 38.71820198239517,
                                "longitude": -9.142133168164838,
                                "estimatePriceInCents":"2700"
                            },
                            {
                                "name":"Bondinho Tour",
                                "type": "TOUR",
                                "latitude": 38.71820198239517,
                                "longitude": -9.142133168164838,
                                "estimatePriceInCents":"2000"
                            }
                        ],
                        "accommodations":[
                            {
                                "name":"ibis hotel",
                                "type": "HOTEL",
                                "latitude": 41.84660739499335,
                                "longitude": 12.592853311316297,
                                "estimatePriceInCents":"8000",
                                "servicesOffering":["wifi", "2 beds", "breakfast", "clean"]
                            }
                        ],
                    "vehicles":[
                        {
                            "company":"Uber",
                            "type":"AIRPLANE",
                            "estimatePriceInCents":30000,
                            "servicesOffering":["wifi", "breakfast"]
                        }
                    ],
                    "needsVisa":false,
                    "adults": 2,
                    "userId":"c1ae16dd-f875-442f-a123-ec467ec0a52a"
                }""")
        }.apply {
            assertEquals(HttpStatusCode.Created, status)
        }

        client.get("/trips?destination=Lisbon"){
            contentType(ContentType.Application.Json)
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val trips = Json.decodeFromString<List<TripResponse>>(bodyAsText())
            assertEquals(1, trips.size)
        }
    }
}