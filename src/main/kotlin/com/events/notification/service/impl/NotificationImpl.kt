package com.events.notification.service.impl

import com.events.notification.controller.response.UserResponseBody
import com.events.notification.enums.NotificationType
import com.events.notification.service.NotificationService
import com.events.notification.service.strategy.NotificationStrategy
import com.events.notification.service.strategy.implementation.EmailNotification
import com.events.notification.service.strategy.implementation.SmsNotification
import com.events.notification.service.strategy.implementation.WhatsAppNotification
import org.springframework.stereotype.Service

@Service
class NotificationImpl : NotificationService {

    private val notificationStrategy: Map<NotificationType, NotificationStrategy>
        get() = mapOf(
            NotificationType.SMS to SmsNotification(),
            NotificationType.WHATSAPP to WhatsAppNotification(),
            NotificationType.EMAIL to EmailNotification()
        )


    override fun sendNotification(userResponseBody: UserResponseBody) {
        userResponseBody.userPreference.userNotification?.forEach { notification ->
            if (notification.active) {
                notificationStrategy[notification.type]?.sendNotification(
                    userResponseBody.email,
                    userResponseBody.phone
                )
            }
        }
    }

}