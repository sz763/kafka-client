package com.github.salavatz.kc.core.rx

import com.github.salavatz.kc.core.BrokerEvent
import com.github.salavatz.kc.core.EventBroker
import com.github.salavatz.kc.ui.MessageConsumerPanel
import io.reactivex.subjects.PublishSubject
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RxEventSubject @Autowired constructor(private val contentPanel: MessageConsumerPanel) : EventBroker, InitializingBean {
    private val subject = PublishSubject.create<BrokerEvent>()
    override fun pushEvent(event: BrokerEvent) {
        subject.onNext(event)
    }

    override fun handleEvent() {
        subject.subscribe { contentPanel.addValue(it.timestamp(), it.key(), it.value()) }
    }

    override fun afterPropertiesSet() {
        handleEvent()
    }
}