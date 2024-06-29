package com.events.notification.service.message.impl

import com.events.notification.service.message.KafkaProducerService
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaEventNotificationProducerServiceImpl(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) : KafkaProducerService {

    private val log = KotlinLogging.logger {}

    @Value("{kafka.topic.event-notification}")
    private lateinit var topic: String

    override fun sendMesssage(message: Any) {
        log.info { "Sending message [$message] to TOPIC [$topic]" }
        kafkaTemplate.send(topic, message)
    }

}