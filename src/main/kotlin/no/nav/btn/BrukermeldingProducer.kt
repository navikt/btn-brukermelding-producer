package no.nav.btn

import io.confluent.kafka.serializers.KafkaJsonSerializer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import java.util.*

private const val TOPIC = "btn-brukermelding"

private val logger = LoggerFactory.getLogger("no.nav.btn.BrukermeldingProducer")
private val producer: KafkaProducer<MessageKey, Message> = KafkaProducer(getProducerConfig(),
        KafkaJsonSerializer<MessageKey>(), KafkaJsonSerializer<Message>())

fun sendMessageToKafka(message: Message) {
    logger.info("Sender melding ${message.message}")
    producer.send(ProducerRecord(TOPIC, MessageKey(UUID.randomUUID()), message))
}

private fun getProducerConfig(): Properties {
    val config = Configuration()
    val props = Properties()

    props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = config.serverUrl
    props[ProducerConfig.CLIENT_ID_CONFIG] = config.clientId
    return props
}