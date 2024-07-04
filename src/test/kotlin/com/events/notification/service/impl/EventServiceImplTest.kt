package com.events.notification.service.impl

import com.events.notification.controller.request.EventRequestBody
import com.events.notification.controller.response.EventResponseBody
import com.events.notification.enums.EventType
import com.events.notification.exception.NotFoundException
import com.events.notification.repository.EventRepository
import com.events.notification.repository.entity.EventEntity
import com.events.notification.service.converter.EventConverter
import com.events.notification.service.message.KafkaProducerService
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@ExtendWith(MockKExtension::class)
class EventServiceImplTest {

    private val ID: String = UUID.randomUUID().toString()

    @MockK
    private lateinit var eventRepository: EventRepository

    @MockK
    private lateinit var eventConverter: EventConverter

    @MockK
    private lateinit var kafkaProducerService: KafkaProducerService

    @InjectMockKs
    private lateinit var eventService: EventServiceImpl

    @Nested
    inner class getAllEvents {
        @Test
        fun `should return all events`() {
            val pageable = PageRequest.of(0, 10)
            val eventEntity = mockk<EventEntity>()
            val eventResponseBody = mockk<EventResponseBody>()

            every { eventRepository.findAll(any(Pageable::class)) } returns PageImpl(listOf(eventEntity), pageable, 1)
            every { eventConverter.toResponseBody(any()) } returns eventResponseBody

            val response = eventService.getAllEvents(0, 10)

            verify { eventRepository.findAll(pageable) }
            verify { eventConverter.toResponseBody(eventEntity) }

            assertFalse { response.isEmpty }
            assertEquals(1, response.totalElements)
            assertEquals(eventResponseBody, response.content[0])
        }

        @Test
        fun `should return empty when no events found`() {
            val pageable = PageRequest.of(0, 10)

            every { eventRepository.findAll(any(Pageable::class)) } returns PageImpl(emptyList(), pageable, 0)

            val response = eventService.getAllEvents(0, 10)

            verify { eventRepository.findAll(pageable) }

            assertTrue { response.isEmpty }
            assertEquals(0, response.totalElements)
        }
    }


    @Nested
    inner class getEventById {
        @Test
        fun `should return event by id`() {
            val eventEntity = mockk<EventEntity>()
            val eventResponseBody = mockk<EventResponseBody>()

            every { eventRepository.findById(any()) } returns Optional.of(eventEntity)
            every { eventConverter.toResponseBody(any()) } returns eventResponseBody

            val response = eventService.getEventById(ID)

            verify { eventRepository.findById(ID) }
            verify { eventConverter.toResponseBody(eventEntity) }

            assertNotNull(response)
            assertEquals(eventResponseBody, response)
        }

        @Test
        fun `should throw exception when event not found`() {
            every { eventRepository.findById(any()) } returns Optional.empty()

            val exception = assertThrows<NotFoundException> { eventService.getEventById(ID) }

            verify { eventRepository.findById(ID) }

            assertEquals("Event Not Found. Id: $ID", exception.message)
        }
    }

    @Test
    fun `should save event and send notification`() {
        val request = EventRequestBody(
            eventType = EventType.FESTIVAL,
            description = "Event description",
            dateTime = LocalDateTime.of(2024, 7, 1, 10, 0, 0)
        )

        val eventEntityToBeSaved = EventEntity(
            id = ID,
            eventType = request.eventType,
            description = request.description,
            dateTime = request.dateTime,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        val responseBody = EventResponseBody(
            id = eventEntityToBeSaved.id,
            eventType = eventEntityToBeSaved.eventType,
            description = eventEntityToBeSaved.description,
            dateTime = eventEntityToBeSaved.dateTime
        )

        every { eventRepository.save(any()) } returns eventEntityToBeSaved
        every { eventConverter.toEntity(any()) } returns eventEntityToBeSaved
        every { eventConverter.toResponseBody(any()) } returns responseBody
        every { kafkaProducerService.sendMesssage(any()) } just Runs

        val response = eventService.saveEvent(request)

        verify { eventRepository.save(any()) }
        verify { eventConverter.toEntity(request) }
        verify { eventConverter.toResponseBody(eventEntityToBeSaved) }
        verify { kafkaProducerService.sendMesssage(response) }

        assertNotNull(response)
    }

    @Nested
    inner class updateEvent {
        @Test
        fun `should update event when event exists`() {
            val requestBody = EventRequestBody(
                eventType = EventType.FESTIVAL,
                description = "Updated description",
                dateTime = LocalDateTime.of(2024, 7, 1, 10, 0, 0)
            )

            val eventEntity = EventEntity(
                id = ID,
                eventType = EventType.MUSIC,
                description = "Old description",
                dateTime = LocalDateTime.of(2024, 7, 1, 10, 0, 0),
                createdAt = LocalDateTime.of(2024, 6, 1, 10, 0, 0),
                updatedAt = LocalDateTime.of(2024, 6, 1, 10, 0, 0)
            )

            val toBeSaved = eventEntity.copy(
                eventType = requestBody.eventType,
                description = requestBody.description,
                dateTime = requestBody.dateTime,
                updatedAt = LocalDateTime.now()
            )


            every { eventRepository.findById(any()) } returns Optional.of(eventEntity)
            every { eventRepository.save(any()) } returns toBeSaved

            eventService.updateEvent(ID, requestBody)

            verify { eventRepository.findById(ID) }
        }

        @Test
        fun `should not update event when event does not exist`() {
            val eventEntity = mockk<EventEntity>()
            val requestBody = mockk<EventRequestBody>()

            every { eventRepository.findById(any()) } returns Optional.empty()

            eventService.updateEvent(ID, requestBody)

            verify { eventRepository.findById(ID) }
            verify(exactly = 0) { eventRepository.save(eventEntity) }
        }

    }

    @Test
    fun `should delete event by id`() {
        every { eventRepository.deleteById(any()) } returns Unit

        eventService.deleteEventById(ID)

        verify { eventRepository.deleteById(ID) }
    }

    @Test
    fun sendNotification() {
        val responseBody = mockk<EventResponseBody>()

        every { kafkaProducerService.sendMesssage(any()) } just Runs

        eventService.sendNotification(responseBody)

        verify { kafkaProducerService.sendMesssage(responseBody) }
    }

}
