package com.github.salavatz.kc.kafka

interface Context {
    fun topicName(name: String)
    fun topicName(): String
}