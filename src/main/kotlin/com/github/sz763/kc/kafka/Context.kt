package com.github.sz763.kc.kafka

interface Context {
    fun topicName(name: String)
    fun topicName(): String
}