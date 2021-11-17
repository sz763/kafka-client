package com.github.svart63.kc.kafka

import com.github.svart63.kc.core.Config
import com.github.svart63.kc.core.EventBroker
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.net.InetAddress
import java.time.Duration
import java.util.Properties

@Service
class KafkaMessageReader @Autowired constructor(
    private val eventBroker: EventBroker,
    private val config: Config
) : DisposableBean {
    private lateinit var streams: KafkaStreams
    private val log = LoggerFactory.getLogger(this::class.java)
    private var isRunning = false
    private var topicName = ""
    private val exceptionHandler = StreamsUncaughtExceptionHandler { exception ->
        log.error("Unexpected error occurred ${exception.message}", exception)
        StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse.SHUTDOWN_APPLICATION
    }

    fun start(topic: String) {
        if (topic == topicName) {
            return
        }
        if (isRunning) {
            stop()
        }
        topicName = topic
        if (config.readFromBeginning()) {
            resetToBeginning(topicName)
        }
        val props = Properties()
        config.asMap("kafka").forEach(props::put)
        val builder = StreamsBuilder()
        builder.stream<ByteArray, ByteArray>(topic)
            .map { k: ByteArray?, v: ByteArray? -> KeyValue(stringOf(k), stringOf(v)) }
            .peek { key, value -> log.info("Received: key={}, value={}", key, value) }
            .foreach { k, v -> eventBroker.pushEvent(Pair(k, v)) }
        streams = KafkaStreams(builder.build(), props)
        streams.setUncaughtExceptionHandler(exceptionHandler)
        log.info("Start reading topic: $topic")
        streams.start()
        isRunning = true
    }

    private fun stringOf(array: ByteArray?): String = array?.let(::String) ?: ""

    private fun stop() {
        log.info("Stop reading topic: $topicName")
        streams.removeStreamThread()
        streams.close(Duration.ofSeconds(10))
        streams.cleanUp()
        isRunning = false
    }

    override fun destroy() {
        stop()
    }

    private fun resetToBeginning(topic: String) {
        val configs = config.asMap("kafka")
        KafkaConsumer<Any, Any>(configs).use { client ->
            resetTopicOffset(client, topic)
        }
    }

    internal fun resetTopicOffset(
        client: KafkaConsumer<Any, Any>,
        topic: String
    ) {
        val partition = client.partitionsFor(topic)
            .map { partInfo -> TopicPartition(partInfo.topic(), partInfo.partition()) }
            .toList()
            .also(client::assign)
            .filter { client.position(it) > 0 }
            .also(client::seekToBeginning)
            .onEach { log.info("For topic '$topicName' position has been reset to: ${client.position(it)}") }
            .firstOrNull()
        partition?.let { client.commitSync() }
    }
}