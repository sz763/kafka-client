package com.github.salavatz.kc.core

interface BrokerEvent {
    fun timestamp(): Long
    fun key(): String
    fun value(): String
}