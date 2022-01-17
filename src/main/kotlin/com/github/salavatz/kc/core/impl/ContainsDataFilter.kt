package com.github.salavatz.kc.core.impl

import com.github.salavatz.kc.core.DataFilter


class ContainsDataFilter(private val expected: String) : DataFilter<String> {
    override fun test(t: String): Boolean = t.contains(expected)
}

class NoopDataFilter<T> : DataFilter<T> {
    override fun test(t: T): Boolean = true
}