package com.events.notification.service.strategy.implementation

import com.events.notification.controller.response.EventResponseBody
import com.events.notification.controller.response.UserResponseBody
import com.events.notification.service.strategy.NotificationStrategy
import mu.KotlinLogging

class WhatsAppNotification : NotificationStrategy {
    private val log = KotlinLogging.logger {}

    override fun sendNotification(user: UserResponseBody, event: EventResponseBody) {
        log.info("Sending WhatsApp notification to [${user.phone}] for Event: [${event.eventType}], description [${event.description}]")
    }

}