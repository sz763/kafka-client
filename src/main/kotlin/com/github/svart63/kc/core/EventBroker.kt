package com.github.svart63.kc.core

interface EventBroker {
    fun pushEvent(event: BrokerEvent)
    fun handleEvent()
}
