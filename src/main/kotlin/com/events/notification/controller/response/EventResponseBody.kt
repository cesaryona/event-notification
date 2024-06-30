package com.events.notification.controller.response

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import java.time.LocalDateTime

data class EventResponseBody(
    val id: String?,
    val eventType: String,
    val description: String,
    val dateTime: LocalDateTime
) {
    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE)
    }
}