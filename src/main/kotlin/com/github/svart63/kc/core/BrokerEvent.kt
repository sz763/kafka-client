package com.github.svart63.kc.core

interface BrokerEvent {
    fun timestamp(): Long
    fun key(): String
    fun value(): String
}