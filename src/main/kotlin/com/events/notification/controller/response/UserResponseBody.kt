package com.events.notification.controller.response

import com.events.notification.repository.entity.UserPreference
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle

data class UserResponseBody(
    val id: String?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val userPreference: UserPreference
) {
    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE)
    }
}