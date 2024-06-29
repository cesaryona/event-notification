package com.events.notification.service

import com.events.notification.controller.response.UserResponseBody

interface NotificationService {

    fun sendNotification(userResponseBody: UserResponseBody)

}