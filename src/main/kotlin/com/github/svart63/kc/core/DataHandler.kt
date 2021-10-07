package com.github.svart63.kc.core

interface DataHandler<T, E, V> {
    fun filter(dataFilter: DataFilter<E>): T
    fun addValues(values: T)
    fun values(): T
    fun addValue(value: V)
}