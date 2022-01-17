package com.github.salavatz.kc.ui.swing.utilities

import java.util.*
import javax.swing.AbstractListModel

class TreeSetModel<T : Comparable<T>> : AbstractListModel<T>() {

    private val data = TreeSet<T>()

    fun addAll(values: Collection<T>) {
        data.addAll(values)
    }

    override fun getSize(): Int = data.size

    override fun getElementAt(index: Int): T = data.elementAt(index)
    fun addElement(value: T) {
        data.add(value)
    }
}