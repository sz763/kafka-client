package com.github.svart63.kc.core.impl

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.github.svart63.kc.core.Config
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileOutputStream
import java.time.ZoneId
import java.util.*
import javax.annotation.PostConstruct

@Component
class YamlConfig : Config {
    private val configName = "./config.yml"
    private val typeRef = object : TypeReference<Map<String, Any>>() {}
    private val mapper = ObjectMapper(YAMLFactory())
    private lateinit var tree: ObjectNode
    private val appIdentifier: String by lazy { "kc_${UUID.randomUUID()}" }


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

    override fun keySerde(): String = tree.get("stream").get("serde.key").asText()

    override fun valueSerde(): String = tree.get("stream").get("serde.value").asText()

    override fun serdePackages(): Array<String> {
        return tree.get("stream").get("serde.packages").map { it.asText() }.toTypedArray()
    }

    override fun serdeTransformer(): String = tree.get("stream").get("serde.transformer").asText()
    override fun dateTimeFormat(): String = tree.get("table")
        .get("date.format")
        .asText("yyyy-MM-dd hh:mm:ss.SSSS")

    override fun timeZoneId(): String = tree.get("table")
        .get("time.zone")
        .asText(ZoneId.systemDefault().id)

    override fun kafkaConfig(): Map<String, Any> {
        val shallAddGroupId = !this.tree.with("kafka").has("group.id")
        val shallAddAppId = !this.tree.with("kafka").has("application.id")
        if (shallAddAppId) {
            this.tree.with("kafka").put("application.id", appIdentifier)
        }
        if (shallAddGroupId) {
            this.tree.with("kafka").put("group.id", appIdentifier)
        }
        if (shallAddGroupId || shallAddAppId) {
            save()
        }
        return asMap("kafka")
    }
}