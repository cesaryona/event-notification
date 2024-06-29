package com.events.notification.service

import com.events.notification.controller.request.EventRequestBody
import com.events.notification.controller.response.EventResponseBody
import com.events.notification.entity.EventEntity
import org.springframework.data.domain.Page

interface UserService {

    fun getAllUsers(page: Int, size: Int): Page<EventResponseBody>

    fun getUserById(id: String): EventEntity

    fun saveUser(request: EventRequestBody): EventEntity

    fun updateUser(id: String, request: EventRequestBody)

    fun deleteUserById(id: String)
}