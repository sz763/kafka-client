package com.github.svart63.kc.ui

interface ContentPanel {
    fun addValue(timestamp: Long, key: String, value: String)
    fun clear()
}