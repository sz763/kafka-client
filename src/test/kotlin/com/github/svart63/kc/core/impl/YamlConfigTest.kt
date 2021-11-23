package com.github.svart63.kc.core.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.context.event.annotation.BeforeTestClass
import kotlin.test.assertEquals

internal class YamlConfigTest {
    private val config = YamlConfig("src/test/resources/test_config.yml")


    @BeforeEach
    fun setUp() {
        config.load()
    }

    @Test
    internal fun testDefaultSchemaArePresent() {
        assertEquals("Darcula", config.defaultTheme())
    }

    @Test
    internal fun testGetKafkaConfig() {
        val map = config.asMap("kafka")
        assertThat(map).containsKey("enable.auto.commit")
    }
}