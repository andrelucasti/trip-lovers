package io.andrelucas

import io.andrelucas.configuration.app.appModule
import io.andrelucas.configuration.trip.tripModule
import io.andrelucas.configuration.user.userModule
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    appModule()

    tripModule()
    userModule()
}
