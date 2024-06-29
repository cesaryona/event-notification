package com.events.notification.controller.request

import com.events.notification.entity.UserPreference
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle

data class UserRequestBody(
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
