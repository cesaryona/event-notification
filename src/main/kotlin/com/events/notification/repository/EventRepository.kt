package com.events.notification.repository

import com.events.notification.repository.entity.EventEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface EventRepository : MongoRepository<EventEntity, String> {
}