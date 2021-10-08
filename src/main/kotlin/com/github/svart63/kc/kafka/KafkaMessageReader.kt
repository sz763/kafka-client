package com.github.svart63.kc.kafka

import com.github.svart63.kc.core.Config
import com.github.svart63.kc.core.EventBroker
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.KeyValueMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
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
    private val exceptionHandler = Thread.UncaughtExceptionHandler { _, exception: Throwable ->
        log.error("Unexpected error occurred ${exception.message}", exception)
    }

    fun start(topic: String) {
        if (topic == topicName) {
            return
        }
        if (isRunning) {
            stop()
        }

        val props = Properties()
        config.asMap("kafka").forEach(props::put)
        val builder = StreamsBuilder()
        builder.stream<String, String>(topic)
            .peek { key, value -> log.info("Received: key={}, value={}", key, value) }
            .foreach { k, v -> eventBroker.pushEvent(Pair(k, v)) }
        streams = KafkaStreams(builder.build(), props)
        streams.setUncaughtExceptionHandler(exceptionHandler)
        log.info("Start reading topic: $topic")
        streams.start()
        isRunning = true
    }

    fun stop() {
        log.info("Stop reading topic")
        streams.close(Duration.ofSeconds(10))

        streams.cleanUp()
        isRunning = false
    }

    override fun destroy() {
        stop()
    }
}