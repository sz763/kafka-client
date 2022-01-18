package com.github.sz763.kc.core

interface TableDataHandler<T, E, V> : DataHandler<T, E, V> {
    fun filter(vararg dataFilter: DataFilter<E>): T
    fun clear()
}