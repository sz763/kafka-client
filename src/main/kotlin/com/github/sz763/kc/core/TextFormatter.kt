package com.github.sz763.kc.core

interface TextFormatter {
    fun format(input: String): String
    fun applicable(input: String): Boolean
}
