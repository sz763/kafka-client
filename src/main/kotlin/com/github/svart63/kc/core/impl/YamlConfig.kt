package com.github.svart63.kc.core.impl

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.github.svart63.kc.core.Config
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileOutputStream
import javax.annotation.PostConstruct

@Component
class YamlConfig : Config {
    private val configName = "./config.yml"
    private val typeRef = object : TypeReference<Map<String, Any>>() {}

    private val mapper = ObjectMapper(YAMLFactory())
    private lateinit var tree: ObjectNode

    @PostConstruct
    override fun load() {
        tree = mapper.readValue(File(configName), ObjectNode::class.java)
    }

    override fun save() {
        FileOutputStream(configName).use {
            mapper.writeValue(it, tree)
        }
    }

    override fun updateTheme(name: String) {
        tree.put("theme", name)
    }

    override fun defaultTheme(): String = tree.get("theme").textValue()

    override fun asMap(key: String, vararg keys: String): Map<String, Any> {
        var node = tree.get(key)
        keys.forEach { node = node.get(it) }
        return mapper.convertValue(node, typeRef)
    }

    override fun readFromBeginning(): Boolean = tree.get("stream").get("read.from.beginning").asBoolean()
}