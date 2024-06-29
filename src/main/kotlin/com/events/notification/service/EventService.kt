package com.events.notification.service

import com.events.notification.controller.request.EventRequestBody
import com.events.notification.controller.response.EventResponseBody
import com.events.notification.entity.EventEntity
import org.springframework.data.domain.Page

interface EventService {

    fun getAllEvents(page: Int, size: Int): Page<EventResponseBody>

    fun getEventById(id: String): EventResponseBody

    fun saveEvent(request: EventRequestBody): EventResponseBody

    fun updateEvent(id: String, request: EventRequestBody)

    fun deleteEventById(id: String)
}