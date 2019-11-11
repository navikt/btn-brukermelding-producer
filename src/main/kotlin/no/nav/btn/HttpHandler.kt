package no.nav.btn

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import no.nav.btn.kafkaservices.Source
import no.nav.btn.packet.Breadcrumb
import no.nav.btn.packet.Packet

private const val CLIENT_ID = "btn-brukermelding-producer"
private val producer = Source(TOPIC_MELDING, CLIENT_ID)

fun createHttpServer(applicationState: ApplicationState): ApplicationEngine = embeddedServer(Netty, 7070) {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            disableHtmlEscaping()
        }
    }

    routing {
        naisRoutes(readinessCheck = { applicationState.initialized }, livenessCheck = { applicationState.running })
        route("/") {
            post {
                producer.producePacket(mapMessageToPacket(call.receive()))
                call.respond(HttpStatusCode.OK)
            }
        }
    }

    applicationState.initialized = true
}

private fun mapMessageToPacket(message: Message): Packet = Packet(
        breadcrumbs = listOf(Breadcrumb(CLIENT_ID)),
        timestamp = message.timestamp,
        melding = message.message
)