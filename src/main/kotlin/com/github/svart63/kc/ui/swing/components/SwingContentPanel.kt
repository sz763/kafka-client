package com.github.svart63.kc.ui.swing.components

import com.github.svart63.kc.core.DataFilter
import com.github.svart63.kc.core.TableDataHandler
import com.github.svart63.kc.core.TextFormatterService
import com.github.svart63.kc.core.impl.ContainsDataFilter
import com.github.svart63.kc.core.impl.NoopDataFilter
import com.github.svart63.kc.ui.ContentPanel
import com.github.svart63.kc.ui.PreviewService
import com.github.svart63.kc.ui.swing.utilities.KeyPressed
import com.github.svart63.kc.ui.swing.utilities.MousePressed
import com.github.svart63.kc.ui.swing.utilities.NotEditableTableModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.awt.BorderLayout
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent.BUTTON2
import java.util.Vector
import javax.swing.JLabel
import javax.swing.JMenuItem
import javax.swing.JOptionPane
import javax.swing.JPopupMenu
import javax.swing.JScrollPane
import javax.swing.JTable

private const val KEY_HEADER = "Key"
private const val VALUE_HEADER = "Value"

@Component
class SwingContentPanel @Autowired constructor(
    @Qualifier("data_handler_table")
    private val dataHandler: TableDataHandler<Vector<Vector<String>>, String, Vector<String>>,
    private val preview: PreviewService,
    private val formatter: TextFormatterService
) : ContentPanel, SwingPanel("Events") {

    private val tableModel = NotEditableTableModel()
    private val table = JTable(tableModel)
    private var valueFilterValue: String = ""
    private var keyFilterValue: String = ""
    private val filterStatus = JLabel("Filtered by: ")
    private var valueFilter: DataFilter<String> = NoopDataFilter()
    private var keyFilter = valueFilter
    private val copyItem = JMenuItem("Copy")
    private val saveItem = SwingSaveMenuItem { formatter.format(cellValue() ?: "") }

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

            if (e.isControlDown && e.isShiftDown && e.keyCode == KeyEvent.VK_C) {
                val cellValue = cellValue()
                copyToClipboard(cellValue)
            } else if (e.isControlDown && e.keyCode == KeyEvent.VK_C) {
                copyCellValue()
            }
        })
        table.addMouseListener(MousePressed { e ->
            if (e.clickCount == 2) {
                preview.show(table.getValueAt(table.selectedRow, 0) as String,
                    table.getValueAt(table.selectedRow, table.selectedColumn) as String)
            }
            if (e.button == BUTTON2) {
                copyCellValue()
            }
            val isCellSelected = table.selectedRow > -1 && table.selectedColumn > -1
            copyItem.isEnabled = isCellSelected
            saveItem.isEnabled = isCellSelected
        })
        tableModel.values(dataHandler.values())
        initTablePopup()
    }

    private fun initTablePopup() {
        val menu = JPopupMenu()
        copyItem.addMouseListener(MousePressed { copyCellValue() })
        copyItem.isEnabled = false
        val clear = JMenuItem("Clear Table")
        clear.addMouseListener(MousePressed {
            val dialog = JOptionPane.showConfirmDialog(
                this,
                "Are you sure want to clear table?",
                "Confirmation",
                JOptionPane.OK_CANCEL_OPTION
            )
            if (dialog == 0) {
                clear()
            }
        })
        val spacer = JMenuItem(" ")
        spacer.isEnabled = false
        menu.add(copyItem)
        menu.add(saveItem)
        menu.add(spacer)
        menu.add(clear)
        table.componentPopupMenu = menu
    }

    private fun copyCellValue() {
        val cellValue = cellValue()?.let { formatter.format(it) }
        copyToClipboard(cellValue)
    }

    private fun copyToClipboard(cellValue: String?) {
        cellValue?.let {
            val selection = StringSelection(it)
            Toolkit.getDefaultToolkit().systemClipboard.setContents(selection, null)
        }
    }

    private fun cellValue(): String? {
        val row = table.selectedRow
        val cell = table.selectedColumn
        if (row < 0 || cell < 0) {
            return null
        }
        return table.getValueAt(row, cell) as String
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

    override fun clear() {
        dataHandler.clear()
        tableModel.clear()
        this.repaint()
    }

    private fun vectorOf(key: String, value: String): Vector<String> {
        val v = Vector<String>()
        v.add(key)
        v.add(value)
        return v
    }

}