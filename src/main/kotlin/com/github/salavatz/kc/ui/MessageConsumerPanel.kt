package com.github.salavatz.kc.ui

interface MessageConsumerPanel {
    fun addValue(timestamp: Long, key: String, value: String)
    fun clear()
}