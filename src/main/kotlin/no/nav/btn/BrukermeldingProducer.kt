package no.nav.btn

import io.confluent.kafka.serializers.KafkaJsonSerializer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import java.util.*

private val logger = LoggerFactory.getLogger("no.nav.btn.BrukermeldingProducer")
private val producer: KafkaProducer<String, Packet> = KafkaProducer(getProducerConfig())

fun sendMessageToKafka(message: Message) {
    logger.info("Sender melding ${message.message}")
    producer.send(ProducerRecord(TOPIC_MELDING_FRA_BRUKER, UUID.randomUUID().toString(), mapMessageToPacket(message)))
}

private fun mapMessageToPacket(message: Message): Packet = Packet(
        breadcrumbs = listOf(Breadcrumb("btn-brukermelding-procucer")),
        timestamp = message.timestamp,
        message = message.message
)

private fun getProducerConfig(): Properties {
    val config = Configuration()
    val props = Properties()

    props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = config.serverUrl
    props[ProducerConfig.CLIENT_ID_CONFIG] = config.clientId
    props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
    props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] =  KafkaJsonSerializer::class.java

    return props
}