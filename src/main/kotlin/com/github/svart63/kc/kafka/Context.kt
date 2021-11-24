package com.github.svart63.kc.kafka

interface Context {
    fun topicName(name: String)
    fun topicName(): String
}