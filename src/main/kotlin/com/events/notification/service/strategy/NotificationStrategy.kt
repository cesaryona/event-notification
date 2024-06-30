package com.events.notification.service.strategy

import com.events.notification.controller.response.EventResponseBody
import com.events.notification.controller.response.UserResponseBody

interface NotificationStrategy {

    fun sendNotification(user: UserResponseBody, event: EventResponseBody)
}