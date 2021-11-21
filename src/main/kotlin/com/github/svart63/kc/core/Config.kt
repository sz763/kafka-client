package com.github.svart63.kc.core

interface Config {
    fun load()
    fun save()
    fun defaultTheme(): String
    fun updateTheme(name: String)
    fun asMap(key: String, vararg keys: String): Map<String, Any>
    fun readFromBeginning(): Boolean
    fun keySerde(): String
    fun valueSerde(): String
    fun serdePackages(): Array<String>
    fun serdeTransformer(): String
    fun dateTimeFormat(): String
    fun timeZoneId(): String
    fun kafkaConfig(): Map<String, Any>
}