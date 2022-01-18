package com.github.sz763.kc.core

interface BrokerEvent {
    fun timestamp(): Long
    fun key(): String
    fun value(): String
}