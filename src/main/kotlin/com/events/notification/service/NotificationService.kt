package com.events.notification.service

import com.events.notification.controller.response.EventResponseBody

interface NotificationService {

    fun sendNotification(eventResponseBody: EventResponseBody)

}