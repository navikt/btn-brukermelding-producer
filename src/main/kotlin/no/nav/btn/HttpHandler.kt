package no.nav.btn

import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun createHttpServer(applicationState: ApplicationState): ApplicationEngine = embeddedServer(Netty, 7070) {
    routing {
        naisRoutes(readinessCheck = { applicationState.initialized }, livenessCheck = { applicationState.running })
        route("/") {
            post {
                sendMessageToKafka(call.receive())
            }
        }
    }

    applicationState.initialized = true
}