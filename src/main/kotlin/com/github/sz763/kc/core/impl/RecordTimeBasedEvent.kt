package com.github.sz763.kc.core.impl

import com.github.sz763.kc.core.BrokerEvent

data class RecordTimeBasedEvent(
    private val timestamp: Long,
    private val key: String,
    private val value: String
) : BrokerEvent {
    override fun timestamp(): Long = timestamp

    override fun key(): String = key

    override fun value(): String = value
}