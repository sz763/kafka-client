package com.github.svart63.kc.core.impl

import com.github.svart63.kc.core.Config
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import kotlin.test.assertEquals

@SpringBootTest
@ContextConfiguration(classes = [YamlConfig::class])
internal class YamlConfigTest @Autowired constructor(private val config: Config) {
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