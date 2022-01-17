package com.github.salavatz.kc.core.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.salavatz.kc.core.TextFormatter
import org.springframework.stereotype.Component

@Component
class JsonFormatter : TextFormatter {
    private val mapper: ObjectMapper = ObjectMapper()
    override fun format(input: String): String {
        return mapper.readTree(input).let { mapper.writerWithDefaultPrettyPrinter().writeValueAsString(it) }
    }

    override fun applicable(input: String): Boolean {
        return (input.startsWith("{") && input.endsWith("}"))
                || (input.startsWith("[") && input.endsWith("]"))
    }

}