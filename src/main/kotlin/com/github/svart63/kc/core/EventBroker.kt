package com.github.svart63.kc.core

interface EventBroker {
    fun pushEvent(event: Pair<String, String>)
    fun handleEvent()
}
