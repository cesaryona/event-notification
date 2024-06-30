package com.events.notification.service

import com.events.notification.controller.request.UserRequestBody
import com.events.notification.controller.response.UserResponseBody
import com.events.notification.enums.EventType
import com.events.notification.enums.NotificationType
import org.springframework.data.domain.Page

interface UserService {

    fun getAllUsers(page: Int, size: Int): Page<UserResponseBody>

    fun getUserById(id: String): UserResponseBody

    fun getUsersByEventType(eventType: EventType): List<UserResponseBody>

    fun saveUser(request: UserRequestBody): UserResponseBody

    fun updateUser(id: String, request: UserRequestBody)

    fun deleteUserById(id: String)
}