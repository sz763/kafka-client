package com.github.svart63.kc.core

interface Config {
    fun load()
    fun save()
    fun defaultTheme(): String
    fun asMap(key: String, vararg keys: String): Map<String, Any>
}