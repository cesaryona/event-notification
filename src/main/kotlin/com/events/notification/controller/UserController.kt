package com.events.notification.controller

import com.events.notification.controller.request.UserRequestBody
import com.events.notification.controller.response.UserResponseBody
import com.events.notification.service.UserService
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/users")
class UserController(private val userService: UserService) {

    @GetMapping("/")
    fun getAllEvents(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<UserResponseBody> {
        return userService.getAllUsers(page, size);
    }

    @GetMapping("/{id}")
    fun getEventById(@PathVariable id: String): UserResponseBody {
        return userService.getUserById(id)
    }

    @PostMapping("/")
    fun saveEvent(@RequestBody request: UserRequestBody): UserResponseBody {
        return userService.saveUser(request)
    }

    @PutMapping("/{id}")
    fun updateEvent(@PathVariable id: String, @RequestBody request: UserRequestBody) {
        userService.updateUser(id, request)
    }

    @DeleteMapping("/{id}")
    fun deleteEventById(@PathVariable id: String) {
        return userService.deleteUserById(id)
    }
}