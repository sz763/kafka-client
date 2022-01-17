package com.github.salavatz.kc.kafka

interface MessageSender {
    fun sendMessage(topicName: String, key: String, value: String, serdeName: String)
}