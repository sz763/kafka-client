package com.github.salavatz.kc.kafka

import org.apache.kafka.common.serialization.Serde

interface SerdeProvider {
    fun keySerde(): Serde<*>
    fun valueSerde(): Serde<*>
    fun transformer(): KafkaTimeBasedTransformer
    fun serdeList(): List<String>
    fun serdeOf(serdeName: String): Serde<*>
}