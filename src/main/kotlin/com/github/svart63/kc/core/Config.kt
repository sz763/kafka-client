package com.github.svart63.kc.core

interface Config {
    fun load()
    fun save()
    fun defaultTheme(): String
}