package com.events.notification.service.converter

import com.events.notification.controller.request.EventRequestBody
import com.events.notification.controller.response.EventResponseBody
import com.events.notification.repository.entity.EventEntity
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class EventConverter {

    fun toEntity(request: EventRequestBody): EventEntity {
        val dateNow = LocalDateTime.now()
        return EventEntity(
            null,
            request.eventType,
            request.description,
            request.dateTime,
            dateNow,
            dateNow
        )
    }

    fun toResponseBody(entity: EventEntity): EventResponseBody {
        return EventResponseBody(
            entity.id,
            entity.eventType,
            entity.description,
            entity.dateTime
        )
    }
}