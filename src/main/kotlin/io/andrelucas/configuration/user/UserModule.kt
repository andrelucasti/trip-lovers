package io.andrelucas.configuration.user

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.userModule() {
    routing {
        get("/users/hello") {
            call.respond("Hello users")
        }
        // Create a user
        post("/users") {
            call.respond("User created")
        }
    }
}