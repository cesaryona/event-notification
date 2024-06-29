package com.events.notification.service.converter

import com.events.notification.controller.request.UserRequestBody
import com.events.notification.controller.response.UserResponseBody
import com.events.notification.repository.entity.UserEntity
import com.events.notification.repository.entity.UserNotification
import com.events.notification.repository.entity.UserPreference
import com.events.notification.enums.NotificationType
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class UserConverter {

    fun toEntity(request: UserRequestBody): UserEntity {
        val dateNow = LocalDateTime.now()
        val userNotifications = request.userPreference.userNotification ?: defaultUserNotifications()

        return UserEntity(
            null,
            request.firstName,
            request.lastName,
            request.email,
            request.phone,
            UserPreference(request.userPreference.eventType, userNotifications),
            dateNow,
            dateNow
        )
    }

    fun toResponseBody(entity: UserEntity): UserResponseBody {
        return UserResponseBody(
            entity.id,
            entity.firstName,
            entity.lastName,
            entity.email,
            entity.phone,
            entity.userPreference
        )
    }

    private fun defaultUserNotifications(): List<UserNotification> {
        return listOf(
            UserNotification(NotificationType.SMS, true),
            UserNotification(NotificationType.EMAIL, true),
            UserNotification(NotificationType.WHATSAPP, true)
        )
    }
}