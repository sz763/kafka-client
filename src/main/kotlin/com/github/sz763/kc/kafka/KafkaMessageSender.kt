package com.github.sz763.kc.kafka

import com.github.sz763.kc.core.Config
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class KafkaMessageSender(private val config: Config, private val serdeProvider: SerdeProvider) : MessageSender {
    private val log = LoggerFactory.getLogger(javaClass)
    private var currentTopic = ""
    private var kafkaProducer: KafkaProducer<Any, Any>? = null
    override fun sendMessage(topicName: String, key: String, value: String, serdeName: String) {
        log.info("Send message to '${topicName}', key = '${key}', value = '${value} ")
        if (currentTopic != topicName) {
            currentTopic = topicName
            kafkaProducer?.close()
            val keySerde: Serde<Any> = serdeProvider.keySerde() as Serde<Any>
            val valueSerde: Serde<Any> = serdeProvider.valueSerde() as Serde<Any>
            kafkaProducer = KafkaProducer(config.kafkaConfig(), keySerde.serializer(), valueSerde.serializer())
        }
        val record = ProducerRecord(
            currentTopic, valueOf(key, serdeProvider.keySerde()), valueOf(value, serdeProvider.valueSerde())
        )
        kafkaProducer?.send(record)
    }

    private fun valueOf(value: String, serde: Serde<*>): Any {
        return when (serde) {
            is Serdes.StringSerde -> value
            is Serdes.ByteArraySerde -> value.toByteArray()
            else -> throw IllegalArgumentException("${serde::class.simpleName} is not supported")
        }
    }
}