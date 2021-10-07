package com.github.svart63.kc.core

interface TopicBroker {
    fun pushTopicName(name: String)
    fun handleTopicName()
}