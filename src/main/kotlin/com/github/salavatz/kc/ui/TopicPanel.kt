package com.github.salavatz.kc.ui

interface TopicPanel {
    fun addTopic(name: String)
    fun addTopics(names: Collection<String>)
}