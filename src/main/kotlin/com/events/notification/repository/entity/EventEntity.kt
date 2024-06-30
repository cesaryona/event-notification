package com.events.notification.repository.entity

import com.events.notification.enums.EventType
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class EventEntity(

    val id: String?,
    val eventType: EventType,
    val description: String,
    val dateTime: LocalDateTime,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime

) {
    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE)
    }
}