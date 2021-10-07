package com.github.svart63.kc.core.impl

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
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

    @Transient
    private val mapper = ObjectMapper(YAMLFactory());
    private lateinit var tree: JsonNode

    @PostConstruct
    override fun load() {
        tree = mapper.readTree(File(configName))
    }

    override fun save() {
        FileOutputStream(configName).use {
            mapper.writeValue(it, tree)
        }
    }

    override fun defaultTheme(): String = tree.get("theme").textValue()

    override fun asMap(key: String, vararg keys: String): Map<String, Any> {
        var node = tree.get(key)
        keys.forEach { node = node.get(it) }
        return mapper.convertValue(node, typeRef)
    }
}