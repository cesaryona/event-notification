package com.events.notification.repository.entity

import com.events.notification.enums.NotificationType
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle

data class UserNotification(

    val type: NotificationType,
    val active: Boolean

) {
    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE)
    }
}
