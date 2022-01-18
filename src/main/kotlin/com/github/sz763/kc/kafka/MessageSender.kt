package com.github.sz763.kc.kafka

interface MessageSender {
    fun sendMessage(topicName: String, key: String, value: String, serdeName: String)
}