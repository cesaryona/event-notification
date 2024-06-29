package com.events.notification.service.impl

import com.events.notification.controller.request.EventRequestBody
import com.events.notification.controller.response.EventResponseBody
import com.events.notification.exception.NotFoundException
import com.events.notification.repository.EventRepository
import com.events.notification.service.EventService
import com.events.notification.service.converter.EventConverter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class EventServiceImpl(
    private val eventRepository: EventRepository,
    private val eventConverter: EventConverter
) : EventService {

    override fun getAllEvents(page: Int, size: Int): Page<EventResponseBody> {
        val pageable = PageRequest.of(page, size)
        val entityList = eventRepository.findAll(pageable)

        if (entityList.isEmpty) {
            return PageImpl(emptyList(), pageable, 0)
        }

        val response = entityList.content.map { eventConverter.toResponseBody(it) }

        return PageImpl(response, pageable, entityList.totalElements)
    }

    override fun getEventById(id: String): EventResponseBody {
        val eventEntity = eventRepository.findById(id)
            .orElseThrow { throw NotFoundException("Event Not Found. Id: $id") }

        return eventConverter.toResponseBody(eventEntity)
    }

    override fun saveEvent(request: EventRequestBody): EventResponseBody {
        val saved = eventRepository.save(eventConverter.toEntity(request))
        return eventConverter.toResponseBody(saved)
    }

    override fun updateEvent(id: String, request: EventRequestBody) {
        eventRepository.findById(id).ifPresent {
            val updatedEvent = it.copy(
                eventType = request.eventType.description,
                description = request.description,
                dateTime = request.dateTime,
                updatedAt = LocalDateTime.now()
            )
            eventRepository.save(updatedEvent)
        }
    }

    override fun deleteEventById(id: String) {
        eventRepository.deleteById(id)
    }

}