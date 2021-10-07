package com.github.svart63.kc.core.rx

import com.github.svart63.kc.core.EventBroker
import com.github.svart63.kc.ui.ContentPanel
import io.reactivex.subjects.PublishSubject
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RxEventSubject @Autowired constructor(private val contentPanel: ContentPanel) : EventBroker, InitializingBean {
    private val subject = PublishSubject.create<Pair<String, String>>()
    override fun pushEvent(event: Pair<String, String>) {
        subject.onNext(event)
    }

    override fun handleEvent() {
        subject.subscribe { contentPanel.addValue(it.first, it.second) }
    }

    override fun afterPropertiesSet() {
        handleEvent()
    }
}