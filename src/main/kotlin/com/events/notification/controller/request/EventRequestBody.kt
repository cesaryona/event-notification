package com.events.notification.controller.request

import com.events.notification.enums.EventType
import java.time.LocalDateTime

data class EventRequestBody(

    val eventType: EventType,
    val description: String,
    val dateTime: LocalDateTime

)
