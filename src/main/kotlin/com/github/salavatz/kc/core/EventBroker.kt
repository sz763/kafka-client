package com.github.salavatz.kc.core

interface EventBroker {
    fun pushEvent(event: BrokerEvent)
    fun handleEvent()
}
