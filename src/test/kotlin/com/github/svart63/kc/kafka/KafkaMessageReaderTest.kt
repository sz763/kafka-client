package com.github.svart63.kc.kafka

import com.github.svart63.kc.core.BrokerEvent
import com.github.svart63.kc.core.Config
import com.github.svart63.kc.core.EventBroker
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.slot
import io.mockk.verify
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.TopologyTestDriver
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class KafkaMessageReaderTest {
    private val props: Properties = Properties()
    private val builder = StreamsBuilder()
    private val broker = mockkClass(EventBroker::class)
    private val slot = slot<BrokerEvent>()
    private val stringSerializer = Serdes.String().serializer()
    private val byteArraySerializer = Serdes.ByteArray().serializer()
    private val longSerializer = Serdes.Long().serializer()
    private val serdeProvider = mockkClass(SerdeProvider::class)
    private val config = mockkClass(Config::class)
    private val reader = KafkaMessageReader(broker, config, serdeProvider)


    @BeforeEach
    internal fun setUp() {
        props[StreamsConfig.APPLICATION_ID_CONFIG] = "test"
        props[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = "dummy:1234"
        every { broker.pushEvent(capture(slot)) }.answers { }
    }

    private val topicName = "test_topic"

    @Test
    internal fun testStreamBuilder() {
        every { broker.pushEvent(capture(slot)) }.answers { }
        reader.initStream(builder, topicName)
        val testDriver = TopologyTestDriver(builder.build(), props)
        val topic = testDriver.createInputTopic(topicName, stringSerializer, stringSerializer)
        topic.pipeInput("test_key", "test_value")
        verify(exactly = 1) { broker.pushEvent(any()) }
        val event = slot.captured
        assertEquals("test_key", event.key())
        assertEquals("test_value", event.value())
        assertTrue(event.timestamp() > 0, "Event timestamp should be greater than 0")
    }

    @Test
    internal fun testStreamByteArray() {
        reader.initStream(builder, topicName)
        val testDriver = TopologyTestDriver(builder.build(), props)
        val topic = testDriver.createInputTopic(topicName, stringSerializer, byteArraySerializer)
        topic.pipeInput("test_key", "test_byte_array_value".toByteArray())
        verify(exactly = 1) { broker.pushEvent(any()) }
        val event = slot.captured
        assertEquals("test_key", event.key())
        assertEquals("test_byte_array_value", event.value())
        assertTrue(event.timestamp() > 0, "Event timestamp should be greater than 0")
    }

    @Test
    internal fun testStreamKeyIsLong() {
        reader.initStream(builder, topicName)
        val testDriver = TopologyTestDriver(builder.build(), props)
        val topic = testDriver.createInputTopic(topicName, longSerializer, byteArraySerializer)
        topic.pipeInput(299_792_458L, "test_byte_array_value".toByteArray())
        verify(exactly = 1) { broker.pushEvent(any()) }
        val event = slot.captured
        assertEquals("299792458", event.key())
        assertEquals("test_byte_array_value", event.value())
        assertTrue(event.timestamp() > 0, "Event timestamp should be greater than 0")
    }
}