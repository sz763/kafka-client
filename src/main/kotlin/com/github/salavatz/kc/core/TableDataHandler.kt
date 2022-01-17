package com.github.salavatz.kc.core

interface TableDataHandler<T, E, V> : DataHandler<T, E, V> {
    fun filter(vararg dataFilter: DataFilter<E>): T
    fun clear()
}