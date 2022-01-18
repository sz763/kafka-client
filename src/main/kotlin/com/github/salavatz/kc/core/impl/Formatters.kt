package com.github.salavatz.kc.core.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.salavatz.kc.core.TextFormatter
import org.springframework.stereotype.Service

abstract class JacksonFormatter : TextFormatter {
    fun jacksonFormatter(input: String, mapper: ObjectMapper): String = mapper.readTree(input)
        .let { mapper.writerWithDefaultPrettyPrinter().writeValueAsString(it) }
}

@Service
class JsonFormatter : JacksonFormatter() {
    private val mapper = ObjectMapper()

    override fun format(input: String) = jacksonFormatter(input, mapper)

    override fun applicable(input: String): Boolean {
        return (input.startsWith("{") && input.endsWith("}"))
                || (input.startsWith("[") && input.endsWith("]"))
    }
}