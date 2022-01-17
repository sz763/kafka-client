package com.github.salavatz.kc.core.impl

import com.github.salavatz.kc.core.TextFormatter
import com.github.salavatz.kc.core.TextFormatterService
import org.springframework.stereotype.Service

@Service
class FormatterService(private val formatters: List<TextFormatter>) : TextFormatterService {
    override fun format(value: String): String = formatters.stream()
        .filter { it.applicable(value) }
        .findFirst()
        .map { it.format(value) }
        .orElse(value)
}