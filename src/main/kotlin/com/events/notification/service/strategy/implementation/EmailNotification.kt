package com.events.notification.service.strategy.implementation

import com.events.notification.controller.response.EventResponseBody
import com.events.notification.controller.response.UserResponseBody
import com.events.notification.service.strategy.NotificationStrategy
import mu.KotlinLogging

class EmailNotification : NotificationStrategy {
    private val log = KotlinLogging.logger {}

    override fun sendNotification(user: UserResponseBody, event: EventResponseBody) {
        log.info("Sending Email notification to [${user.email}] for Event: [${event.eventType}], description [${event.description}]")
    }

}