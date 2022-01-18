package com.github.sz763.kc.kafka

import org.springframework.stereotype.Component

@Component
class KafkaContext : Context {
    private var topicName: String = ""
    override fun topicName(name: String) {
        this.topicName = name
    }

    override fun topicName(): String = this.topicName
}