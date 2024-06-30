package com.events.notification.repository

import com.events.notification.enums.EventType
import com.events.notification.enums.NotificationType
import com.events.notification.repository.entity.UserEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<UserEntity, String> {

    fun findByUserPreferenceEventType(eventType: EventType): List<UserEntity>

}