package com.events.notification.service.strategy

interface NotificationStrategy {

    fun sendNotification(email: String, phone: String)
}