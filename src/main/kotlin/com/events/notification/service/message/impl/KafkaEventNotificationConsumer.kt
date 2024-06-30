package com.events.notification.service.message.impl

import com.events.notification.controller.response.EventResponseBody
import com.events.notification.service.NotificationService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component


@Component
class KafkaEventNotificationConsumer(
    private val notificationService: NotificationService,
    private val objectMapper: ObjectMapper
) {
    private val log = KotlinLogging.logger {}

    @KafkaListener(topics = ["\${kafka.topic.event-notification}"])
    fun listen(message: String) {
        log.info { "Received message: [$message]" }

        val event: EventResponseBody = objectMapper.readValue(message)
        notificationService.sendNotification(event)
    }
}