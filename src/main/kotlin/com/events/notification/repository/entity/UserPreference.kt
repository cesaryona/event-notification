package com.events.notification.repository.entity

import com.events.notification.enums.EventType
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle

data class UserPreference(

    val eventType: List<EventType>,
    val userNotification: List<UserNotification>?

) {
    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE)
    }
}