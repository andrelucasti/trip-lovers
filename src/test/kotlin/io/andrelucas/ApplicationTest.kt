package io.andrelucas

import io.andrelucas.configuration.trip.tripModule
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals


class ApplicationTest {

    @Ignore
    @Test
    fun testRoot() = testApplication {
        application {
            tripModule()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }
}
