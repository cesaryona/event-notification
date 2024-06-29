package com.events.notification.service.strategy.implementation

import com.events.notification.service.strategy.NotificationStrategy
import mu.KotlinLogging

class WhatsAppNotification : NotificationStrategy {
    private val log = KotlinLogging.logger {}

    override fun sendNotification(email: String, phone: String) {
        log.info("Sending WhatsApp notification to $phone")
    }

}