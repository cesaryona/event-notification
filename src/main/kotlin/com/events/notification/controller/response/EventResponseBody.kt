package com.events.notification.controller.response

import java.time.LocalDateTime

data class EventResponseBody(
    val id: String?,
    val eventType: String,
    val description: String,
    val dateTime: LocalDateTime = LocalDateTime.now()
)
