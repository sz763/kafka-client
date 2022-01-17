package com.github.salavatz.kc.ui.swing.utilities

import java.util.Vector
import javax.swing.table.DefaultTableModel

class NotEditableTableModel : DefaultTableModel() {
    override fun isCellEditable(row: Int, column: Int): Boolean = false
    fun clear() = dataVector.clear()
    fun values(values: Vector<Vector<String>>) {
        dataVector.addAll(values)
    }
}