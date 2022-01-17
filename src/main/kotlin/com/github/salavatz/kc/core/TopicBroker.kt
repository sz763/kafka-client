package com.github.salavatz.kc.core

interface TopicBroker {
    fun pushTopicName(name: String)
    fun handleTopicName()
}