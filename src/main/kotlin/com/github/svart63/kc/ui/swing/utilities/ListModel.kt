package com.github.svart63.kc.ui.swing.utilities

import java.util.Vector
import javax.swing.AbstractListModel
import javax.swing.DefaultListModel

class ListModel<T> : AbstractListModel<T>() {

    private val data = Vector<T>()

    fun addAll(values: Collection<T>) {
        data.addAll(values)
    }

    override fun getSize(): Int = data.size

    override fun getElementAt(index: Int): T = data[index]
    fun addElement(value: T) {
        data.addElement(value)
    }

    fun clear() = data.clear()
}