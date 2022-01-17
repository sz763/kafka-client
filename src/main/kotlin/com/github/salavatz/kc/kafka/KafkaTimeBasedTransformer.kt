package com.github.salavatz.kc.kafka

import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.Transformer
import org.apache.kafka.streams.processor.ProcessorContext

abstract class KafkaTimeBasedTransformer : Transformer<Any, Any, KeyValue<Long, Pair<String, String>>> {
    private lateinit var context: ProcessorContext

    override fun init(context: ProcessorContext?) {
        this.context = context!!
    }


    override fun close() {
        //nothing to do
    }

    override fun transform(key: Any?, value: Any?): KeyValue<Long, Pair<String, String>> {
        return KeyValue(context.timestamp(), Pair(convertKey(key), convertValue(value)))
    }

    abstract fun convertKey(key: Any?): String

    abstract fun convertValue(value: Any?): String

}