package com.github.svart63.kc.kafka

import com.github.svart63.kc.core.Config
import com.github.svart63.kc.core.TopicBroker
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class KafkaTopicService @Autowired constructor(
    private val config: Config,
    private val topicBroker: TopicBroker
) : InitializingBean {
    override fun afterPropertiesSet() {
        KafkaConsumer<Any, Any>(config.asMap("kafka")).use {
            it.listTopics().map { (k, _) -> k }.forEach(topicBroker::pushTopicName)
        }
    }
}