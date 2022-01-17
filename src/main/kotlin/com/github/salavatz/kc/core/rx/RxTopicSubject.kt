package com.github.salavatz.kc.core.rx

import com.github.salavatz.kc.core.TopicBroker
import com.github.salavatz.kc.ui.TopicPanel
import io.reactivex.subjects.PublishSubject
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RxTopicSubject @Autowired constructor(
    private val topicPanel: TopicPanel
) : TopicBroker, InitializingBean {
    private val subject = PublishSubject.create<String>()

    override fun handleTopicName() {
        subject.subscribe(topicPanel::addTopic)
    }

    override fun pushTopicName(name: String) {
        subject.onNext(name)
    }

    override fun afterPropertiesSet() {
        handleTopicName()
    }
}