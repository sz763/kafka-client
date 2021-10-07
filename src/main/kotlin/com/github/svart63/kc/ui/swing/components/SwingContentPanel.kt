package com.github.svart63.kc.ui.swing.components

import com.github.svart63.kc.core.DataFilter
import com.github.svart63.kc.core.TableDataHandler
import com.github.svart63.kc.core.impl.ContainsDataFilter
import com.github.svart63.kc.core.impl.NoopDataFilter
import com.github.svart63.kc.ui.ContentPanel
import com.github.svart63.kc.ui.PreviewService
import com.github.svart63.kc.ui.swing.SwingPanel
import com.github.svart63.kc.ui.swing.utilities.KeyPressed
import com.github.svart63.kc.ui.swing.utilities.MousePressed
import com.github.svart63.kc.ui.swing.utilities.NotEditableTableModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.awt.BorderLayout
import java.awt.event.KeyEvent
import java.util.*
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JScrollPane
import javax.swing.JTable

@Component
class SwingContentPanel @Autowired constructor(
    @Qualifier("data_handler_table")
    private val dataHandler: TableDataHandler<Vector<Vector<String>>, String, Vector<String>>,
    private val preview: PreviewService
) : ContentPanel, SwingPanel("Events") {
    private val KEY_HEADER = "Key"
    private val VALUE_HEADER = "Value"
    private val tableModel = NotEditableTableModel()
    private val table = JTable(tableModel)
    private var valueFilterValue: String = ""
    private var keyFilterValue: String = ""
    private val filterStatus = JLabel("Filtered by: ")
    private var valueFilter: DataFilter<String> = NoopDataFilter()
    private var keyFilter = valueFilter

    init {
        initTable()
        initContainer()
        initStatus()
    }

    private fun initStatus() {
        this.add(filterStatus, BorderLayout.PAGE_END)
        filterStatus.isVisible = false
    }

    private fun initContainer() {
        val container = JScrollPane(table)
        container.horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        container.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        add(container)
    }

    private fun initTable() {
        tableModel.addColumn(KEY_HEADER)
        tableModel.addColumn(VALUE_HEADER)
        val keyColumn = table.getColumn(KEY_HEADER)
        keyColumn.preferredWidth = 300
        keyColumn.minWidth = 100
        keyColumn.maxWidth = 300
        table.addKeyListener(KeyPressed { e ->
            if (e.isControlDown && e.isShiftDown && e.keyCode == KeyEvent.VK_F) {
                handleKeyFilter()
            } else if (e.isControlDown && e.keyCode == KeyEvent.VK_F) {
                handleValueFilter()
            }
            if (e.isControlDown && e.keyCode == KeyEvent.VK_F) {
                tableModel.clear()
                applyFilter()
            }
        })
        table.addMouseListener(MousePressed { e ->
            if (e.clickCount == 2) {
                preview.show(table.getValueAt(table.selectedRow, table.selectedColumn) as String)
            }
        })
        tableModel.values(dataHandler.values())
    }

    private fun handleKeyFilter() {
        filterPopup("$KEY_HEADER should contain", keyFilterValue)?.let {
            keyFilterValue = it
            keyFilter = ContainsDataFilter(it)
            updateHeader(it, KEY_HEADER)
        }
    }

    private fun handleValueFilter() {
        filterPopup("$VALUE_HEADER should contain", valueFilterValue)?.let {
            valueFilterValue = it
            valueFilter = ContainsDataFilter(it)
            updateHeader(it, VALUE_HEADER)
        }
    }

    private fun updateHeader(it: String, header: String) {
        val column = table.getColumn(header)
        column.headerValue = if (it.isEmpty()) header else "$header (filtered by: $it)"
        column.identifier = header
    }

    private fun filterPopup(filterMessage: String, value: String): String? {
        return JOptionPane.showInputDialog(this, filterMessage, value)
    }

    private fun applyFilter() {
        val list = dataHandler.filter(keyFilter, valueFilter)
        list.forEach(tableModel::addRow)
    }

    override fun addValue(key: String, value: String) {
        if (keyFilter.test(key) && valueFilter.test(value)) {
            val data = vectorOf(key, value)
            dataHandler.addValue(data)
            tableModel.addRow(data)
        }
    }

    private fun vectorOf(key: String, value: String): Vector<String> {
        val v = Vector<String>()
        v.add(key)
        v.add(value)
        return v
    }

}