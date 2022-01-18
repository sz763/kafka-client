package com.github.sz763.kc.core.impl

import com.github.sz763.kc.core.TextFormatter
import com.github.sz763.kc.core.TextFormatterService
import org.springframework.stereotype.Service

@Service
class FormatterService(private val formatters: List<TextFormatter>) : TextFormatterService {
    override fun format(value: String): String = formatters.stream()
        .filter { it.applicable(value) }
        .findFirst()
        .map { it.format(value) }
        .orElse(value)
}