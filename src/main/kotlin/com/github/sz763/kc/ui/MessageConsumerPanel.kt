package com.github.sz763.kc.ui

interface MessageConsumerPanel {
    fun addValue(timestamp: Long, key: String, value: String)
    fun clear()
}