package com.github.svart63.kc.core.impl

import com.github.svart63.kc.core.DataFilter
import com.github.svart63.kc.core.DataHandler
import com.github.svart63.kc.core.TableDataHandler
import java.util.*

class ListDataHandler : DataHandler<List<String>, String, String> {
    private var data: List<String> = mutableListOf()
    override fun filter(dataFilter: DataFilter<String>): List<String> = data.filter(dataFilter::test)

    override fun addValues(values: List<String>) {
        (data as MutableList<String>).addAll(values)
    }

    override fun values(): List<String> = data
    override fun addValue(value: String) {
        (data as MutableList<String>).add(value)
    }
}

class VectorDataHandler : TableDataHandler<Vector<Vector<String>>, String, Vector<String>> {
    private var data = Vector<Vector<String>>()
    override fun filter(dataFilter: DataFilter<String>): Vector<Vector<String>> {
        return Vector(data.filter { it.any(dataFilter::test) })
    }

    override fun addValues(values: Vector<Vector<String>>) {
        data.addAll(values)
    }

    override fun values(): Vector<Vector<String>> = data
    override fun addValue(value: Vector<String>) {
        data.add(value)
    }

    override fun filter(vararg dataFilter: DataFilter<String>): Vector<Vector<String>> {
        return Vector(data.filter {
            var shouldBeDisplayed = true
            dataFilter.forEachIndexed { index, dataFilter ->
                shouldBeDisplayed = shouldBeDisplayed && dataFilter.test(it[index])
            }
            shouldBeDisplayed
        })
    }

}