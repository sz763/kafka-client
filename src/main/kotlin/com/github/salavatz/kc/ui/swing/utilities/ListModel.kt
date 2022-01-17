package com.github.salavatz.kc.ui.swing.utilities

import java.util.*
import javax.swing.AbstractListModel

class ListModel<T : Comparable<T>> : AbstractListModel<T>() {

    private val data = Vector<T>()

    fun addAll(values: Collection<T>) {
        data.addAll(values)
    }

    override fun getSize(): Int = data.size

    override fun getElementAt(index: Int): T = data[index]

    fun addElement(value: T) {
        val index = data.size
        data.addElement(value)
        fireIntervalAdded(this, index, index)
    }

    fun list() = data

    fun clear() = data.clear()

    fun sort() = data.sort()
    fun removeItem(value: T) {
        data.remove(value)
        val index = data.size
        fireIntervalAdded(this, index, index)
    }
}