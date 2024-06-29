package com.events.notification.service.impl

import com.events.notification.controller.request.UserRequestBody
import com.events.notification.controller.response.UserResponseBody
import com.events.notification.exception.NotFoundException
import com.events.notification.repository.UserRepository
import com.events.notification.service.UserService
import com.events.notification.service.converter.UserConverter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userConverter: UserConverter
) : UserService {

    override fun getAllUsers(page: Int, size: Int): Page<UserResponseBody> {
        val pageable = PageRequest.of(page, size)
        val userList = userRepository.findAll(pageable)

        if (userList.isEmpty) {
            return PageImpl(emptyList(), pageable, 0)
        }

        val response = userList.content.map { userConverter.toResponseBody(it) }

        return PageImpl(response, pageable, userList.totalElements)
    }

    override fun getUserById(id: String): UserResponseBody {
        val userEntity = userRepository.findById(id)
            .orElseThrow { throw NotFoundException("User Not Found. Id: $id") }

        return userConverter.toResponseBody(userEntity)
    }

    override fun saveUser(request: UserRequestBody): UserResponseBody {
        val saved = userRepository.save(userConverter.toEntity(request))
        return userConverter.toResponseBody(saved)
    }

    override fun updateUser(id: String, request: UserRequestBody) {
        userRepository.findById(id).ifPresent {
            val current = it.copy(
                firstName = request.firstName,
                lastName = request.lastName,
                email = request.email,
                phone = request.phone,
                userPreference = request.userPreference,
                updatedAt = LocalDateTime.now()
            )

            userRepository.save(current)
        }
    }

    override fun deleteUserById(id: String) {
        userRepository.deleteById(id)
    }
}