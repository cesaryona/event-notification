package com.events.notification.service.message

interface KafkaProducerService {

    fun sendMesssage(message: Any)

}