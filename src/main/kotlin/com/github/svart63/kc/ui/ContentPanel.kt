package com.github.svart63.kc.ui

import java.sql.Timestamp

interface ContentPanel {
    fun addValue(timestamp: Long, key: String, value: String)
    fun clear()
}