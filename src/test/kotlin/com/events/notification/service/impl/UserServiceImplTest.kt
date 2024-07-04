package com.events.notification.service.impl

import com.events.notification.controller.request.UserRequestBody
import com.events.notification.controller.response.UserResponseBody
import com.events.notification.enums.EventType
import com.events.notification.enums.NotificationType
import com.events.notification.exception.NotFoundException
import com.events.notification.repository.UserRepository
import com.events.notification.repository.entity.UserEntity
import com.events.notification.repository.entity.UserNotification
import com.events.notification.repository.entity.UserPreference
import com.events.notification.service.converter.UserConverter
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
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
class UserServiceImplTest {

    private val ID: String = UUID.randomUUID().toString()

    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var userConverter: UserConverter

    @InjectMockKs
    private lateinit var userService: UserServiceImpl

    @Nested
    inner class getUsers {
        @Test
        fun `should return all users`() {
            val pageable = PageRequest.of(0, 10)
            val userEntity = mockk<UserEntity>()
            val userResponseBody = mockk<UserResponseBody>()

            every { userRepository.findAll(any(Pageable::class)) } returns PageImpl(listOf(userEntity), pageable, 1)
            every { userConverter.toResponseBody(any()) } returns userResponseBody

            val response = userService.getAllUsers(0, 10)

            verify { userRepository.findAll(pageable) }
            verify { userConverter.toResponseBody(userEntity) }

            assertFalse { response.isEmpty }
            assertEquals(1, response.totalElements)
            assertEquals(userResponseBody, response.content[0])
        }

        @Test
        fun `should return empty when no users found`() {
            val pageable = PageRequest.of(0, 10)

            every { userRepository.findAll(any(Pageable::class)) } returns PageImpl(emptyList(), pageable, 1)

            val response = userService.getAllUsers(0, 10)

            verify { userRepository.findAll(pageable) }

            assertTrue { response.isEmpty }
            assertEquals(0, response.totalElements)
        }
    }

    @Nested
    inner class getUserById {
        @Test
        fun `should return event by id`() {
            val userEntity = mockk<UserEntity>()
            val userResponseBody = mockk<UserResponseBody>()

            every { userRepository.findById(any()) } returns Optional.of(userEntity)
            every { userConverter.toResponseBody(any()) } returns userResponseBody

            val response = userService.getUserById(ID)

            verify { userRepository.findById(ID) }
            verify { userConverter.toResponseBody(userEntity) }

            assertNotNull(response)
            assertEquals(userResponseBody, response)
        }

        @Test
        fun `should throw exception when event not found`() {
            every { userRepository.findById(any()) } returns Optional.empty()

            val exception = assertThrows<NotFoundException> { userService.getUserById(ID) }

            verify { userRepository.findById(ID) }

            assertEquals("User Not Found. Id: $ID", exception.message)
        }
    }

    @Test
    fun `should get user by event type`() {
        val userEntity = mockk<UserEntity>()
        val userResponseBody = mockk<UserResponseBody>()

        every { userRepository.findByUserPreferenceEventType(any()) } returns listOf(userEntity)
        every { userConverter.toResponseBody(any()) } returns userResponseBody

        val response = userService.getUsersByEventType(EventType.MUSIC)

        verify { userRepository.findByUserPreferenceEventType(EventType.MUSIC) }
        verify { userConverter.toResponseBody(userEntity) }

        assertNotNull(response)
        assertEquals(1, response.size)
    }

    @Test
    fun `should save user`() {
        val userEntity = mockk<UserEntity>()
        val userRequestBody = mockk<UserRequestBody>()
        val userResponseBody = mockk<UserResponseBody>()

        every { userConverter.toEntity(any()) } returns userEntity
        every { userRepository.save(any()) } returns userEntity
        every { userConverter.toResponseBody(any()) } returns userResponseBody

        val response = userService.saveUser(userRequestBody)

        verify { userConverter.toEntity(userRequestBody) }
        verify { userRepository.save(userEntity) }
        verify { userConverter.toResponseBody(userEntity) }

        assertNotNull(response)
    }

    @Test
    fun `should update user`() {
        val userNotification = UserNotification(
            NotificationType.SMS, true
        )

        val userPreference = UserPreference(
            listOf(EventType.MUSIC, EventType.FESTIVAL),
            listOf(userNotification)
        )

        val userEntity = UserEntity(
            ID,
            "John",
            "Doe",
            "email@email.com",
            "123456789",
            userPreference,
            LocalDateTime.of(2024, 7, 1, 10, 0, 0),
            LocalDateTime.of(2024, 7, 1, 10, 0, 0)
        )

        val userRequestBody = UserRequestBody(
            "Jonny",
            "Doe",
            "email@email.com",
            "123456789",
            userPreference
        )

        val userUpdated = UserEntity(
            ID,
            userRequestBody.firstName,
            userRequestBody.lastName,
            userRequestBody.email,
            userRequestBody.phone,
            userRequestBody.userPreference,
            LocalDateTime.of(2024, 7, 1, 10, 0, 0),
            LocalDateTime.of(2024, 7, 1, 11, 0, 0)
        )


        every { userRepository.findById(any()) } returns Optional.of(userEntity)
        every { userRepository.save(any()) } returns userUpdated

        userService.updateUser(ID, userRequestBody)

        verify { userRepository.findById(ID) }
    }

    @Test
    fun `should delete user by id`() {
        every { userRepository.deleteById(any()) } returns Unit

        userService.deleteUserById(ID)

        verify { userRepository.deleteById(ID) }
    }

}