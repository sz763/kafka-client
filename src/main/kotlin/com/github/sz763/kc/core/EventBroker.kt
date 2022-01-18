package com.github.sz763.kc.core

interface EventBroker {
    fun pushEvent(event: BrokerEvent)
    fun handleEvent()
}
