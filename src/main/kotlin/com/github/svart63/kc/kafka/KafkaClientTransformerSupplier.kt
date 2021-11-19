package com.github.svart63.kc.kafka

import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.Transformer
import org.apache.kafka.streams.kstream.TransformerSupplier

class KafkaClientTransformerSupplier<T>(private val kafkaClientTransformer: T) :
    TransformerSupplier<Any, Any, KeyValue<Long, Pair<String, String>>> where T : KafkaTimeBasedTransformer {
    override fun get(): Transformer<Any, Any, KeyValue<Long, Pair<String, String>>> {
        return kafkaClientTransformer
    }
}