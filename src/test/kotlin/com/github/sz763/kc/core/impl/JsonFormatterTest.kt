package com.github.sz763.kc.core.impl

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class JsonFormatterTest {
    @Test
    internal fun testJsonFormatter() {
        val formatter = JsonFormatter()
        val format = formatter.format("{\"a\":1}")
        val sep = System.lineSeparator()
        val expected = "{$sep  \"a\" : 1$sep}"
        assertEquals(expected.trim(), format)
    }
}