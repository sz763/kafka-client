package com.github.svart63.kc.ui

interface MessageConsumerPanel {
    fun addValue(timestamp: Long, key: String, value: String)
    fun clear()
}