package com.events.notification.service.impl

import com.events.notification.controller.response.EventResponseBody
import com.events.notification.controller.response.UserResponseBody
import com.events.notification.enums.NotificationType
import com.events.notification.repository.entity.UserNotification
import com.events.notification.service.NotificationService
import com.events.notification.service.UserService
import com.events.notification.service.strategy.NotificationStrategy
import com.events.notification.service.strategy.implementation.EmailNotification
import com.events.notification.service.strategy.implementation.SmsNotification
import com.events.notification.service.strategy.implementation.WhatsAppNotification
import org.springframework.stereotype.Service

@Service
class NotificationImpl(
    private val userService: UserService
) : NotificationService {

    private val notificationStrategy: Map<NotificationType, NotificationStrategy>
        get() = mapOf(
            NotificationType.SMS to SmsNotification(),
            NotificationType.WHATSAPP to WhatsAppNotification(),
            NotificationType.EMAIL to EmailNotification()
        )

    override fun sendNotification(eventResponseBody: EventResponseBody) {
        val users = userService.getUsersByEventType(eventResponseBody.eventType)
        users.forEach { user ->
            user.userPreference.userNotification?.forEach { userNotification ->
                notificate(userNotification, user, eventResponseBody)
            }
        }
    }

    private fun notificate(userNotification: UserNotification, user: UserResponseBody, event: EventResponseBody) {
        if (userNotification.active) {
            notificationStrategy[userNotification.type]?.sendNotification(user, event)
        }
    }

}