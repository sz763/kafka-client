package com.github.salavatz.kc.core.impl

import com.github.salavatz.kc.core.TextFormatter
import io.mockk.every
import io.mockk.mockkClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class FormatterServiceTest {
    @Test
    internal fun testRawFormatIfNoApplicable() {
        val mockFormatter = mockkClass(TextFormatter::class)
        every { mockFormatter.applicable(any()) } returns false
        every { mockFormatter.format(any()) } returns ""
        val service = FormatterService(listOf(mockFormatter))
        assertThat(service.format("[1,2,3]")).isEqualTo("[1,2,3]")
    }

    @Test
    internal fun testFormattedIfFormatterApplicable() {
        val mockFormatter = mockkClass(TextFormatter::class)
        every { mockFormatter.applicable(any()) } returns true
        every { mockFormatter.format(any()) } returns "1,2,3"
        val service = FormatterService(listOf(mockFormatter))
        assertThat(service.format("[1,2,3]")).isEqualTo("1,2,3")
    }
}