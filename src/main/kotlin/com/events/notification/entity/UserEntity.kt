package com.events.notification.entity

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class UserEntity(

    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val userPreference: UserPreference,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime

) {
    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE)
    }
}
