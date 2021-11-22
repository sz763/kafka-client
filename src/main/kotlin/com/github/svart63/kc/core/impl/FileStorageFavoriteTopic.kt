package com.github.svart63.kc.core.impl

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.github.svart63.kc.core.FavoriteTopics
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

@Component
class FileStorageFavoriteTopic : FavoriteTopics, InitializingBean {
    private val fileStorage = "./favorite_topics.yml"
    private val mapper = ObjectMapper(YAMLFactory())
    private val topics: MutableList<String> = mutableListOf()
    override fun add(name: String) {
        if (!topics.contains(name)) {
            topics.add(name)
        }
    }

    override fun save() {
        FileOutputStream(fileStorage).use { mapper.writeValue(it, topics) }
    }

    override fun remove(name: String) {
        topics.removeIf { it == name }
    }

    override fun topics(): Collection<String> = Collections.unmodifiableCollection(topics)
    override fun load() {
        val file = File(fileStorage)
        if (file.exists()) {
            FileInputStream(file).use {
                topics.addAll(mapper.readValue(it, object : TypeReference<MutableList<String>>() {}))
            }
        }
    }

    override fun afterPropertiesSet() {
        load()
    }
}