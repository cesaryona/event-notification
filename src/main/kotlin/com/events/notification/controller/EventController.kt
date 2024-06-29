package com.events.notification.controller

import com.events.notification.controller.request.EventRequestBody
import com.events.notification.controller.response.EventResponseBody
import com.events.notification.entity.EventEntity
import com.events.notification.service.EventService
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/events")
class EventController(private val eventService: EventService) {

    @GetMapping("/")
    fun getAllEvents(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<EventResponseBody> {
        return eventService.getAllEvents(page, size);
    }

    @GetMapping("/{id}")
    fun getEventById(@PathVariable id: String): EventResponseBody {
        return eventService.getEventById(id)
    }

    @PostMapping("/")
    fun saveEvent(@RequestBody request: EventRequestBody): EventResponseBody {
        return eventService.saveEvent(request)
    }

    @PutMapping("/{id}")
    fun updateEvent(@PathVariable id: String, @RequestBody request: EventRequestBody) {
        eventService.updateEvent(id, request)
    }

    @DeleteMapping("/{id}")
    fun deleteEventById(@PathVariable id: String) {
        return eventService.deleteEventById(id)
    }

}