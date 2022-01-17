package com.github.salavatz.kc.kafka

import com.github.salavatz.kc.core.Config
import com.github.salavatz.kc.core.TopicBroker
import com.github.salavatz.kc.ui.ErrorNotifier
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class KafkaTopicService @Autowired constructor(
    private val config: Config,
    private val topicBroker: TopicBroker,
    private val errorNotifier: ErrorNotifier
) : InitializingBean {
    override fun afterPropertiesSet() {
        val consumer = KafkaConsumer<Any, Any>(config.asMap("kafka"))
        try {
            consumer.use {
                it.listTopics().map { (k, _) -> k }.forEach(topicBroker::pushTopicName)
            }
        } catch (e: Exception) {
            errorNotifier.notify(
                "Error occurred during getting topic names: ${e.message},\n\n" +
                        "Check more details in log file."
            )
        }
    }
}