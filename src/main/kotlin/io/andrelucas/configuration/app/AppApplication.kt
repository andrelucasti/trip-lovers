package io.andrelucas.configuration.app

import io.ktor.server.application.*

fun Application.appModule() {
    configureSerialization()
    configureDatabases()
    configureMonitoring()
}