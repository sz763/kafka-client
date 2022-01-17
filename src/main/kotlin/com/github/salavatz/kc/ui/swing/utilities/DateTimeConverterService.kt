package com.github.salavatz.kc.ui.swing.utilities

import com.github.salavatz.kc.core.Config
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Service
class DateTimeConverterService(private val config: Config) : InitializingBean {
    private lateinit var dateTimeFormatter: DateTimeFormatter
    fun convert(timestamp: Long): String = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(timestamp), ZoneId.of(config.timeZoneId())
    ).format(dateTimeFormatter)

    override fun afterPropertiesSet() {
        dateTimeFormatter = DateTimeFormatter.ofPattern(config.dateTimeFormat())
    }
}