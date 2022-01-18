package com.github.sz763.kc.core

interface TopicBroker {
    fun pushTopicName(name: String)
    fun handleTopicName()
}