package com.github.salavatz.kc.ui.swing.components

import com.github.salavatz.kc.core.TextFormatterService
import com.github.salavatz.kc.ui.ClipboardService
import com.github.salavatz.kc.ui.PreviewService
import com.github.salavatz.kc.ui.swing.utilities.MousePressed
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.awt.BorderLayout
import java.awt.BorderLayout.CENTER
import java.awt.Dimension
import javax.swing.*

@Component
class SwingPreview @Autowired constructor(
    private val formatterService: TextFormatterService,
    private val clipboardService: ClipboardService,
    private val mainFrame: JFrame
) : PreviewService {
    override fun show(title: String, value: String) {
        val (area, saveMenuItem) = initTextArea(value)
        val container = initContainer(area)
        val menu = initMenu(area, saveMenuItem)
        initJframe(title, container, menu)
    }

    private fun initTextArea(value: String): Pair<JTextArea, SwingSaveMenuItem> {
        val area = JTextArea()
        val saveMenuItem = SwingSaveMenuItem { area.text }
        area.text = formatterService.format(value)
        area.componentPopupMenu.add(saveMenuItem)
        area.caretPosition = 0
        return Pair(area, saveMenuItem)
    }

    private fun initContainer(area: JTextArea): JScrollPane {
        val container = JScrollPane(area)
        container.horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        container.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        return container
    }

    private fun initMenu(
        area: JTextArea,
        saveMenuItem: SwingSaveMenuItem
    ): JMenu {
        val menu = JMenu("Actions")
        val copyAll = JMenuItem("Copy All")
        copyAll.addMouseListener(MousePressed { clipboardService.copy(area.text) })
        menu.add(copyAll)
        menu.add(saveMenuItem)
        return menu
    }

    private fun initJframe(title: String, container: JScrollPane, menu: JMenu) {
        val frame = JFrame("Preview of '$title'")
        frame.layout = BorderLayout()
        frame.add(container, CENTER)
        frame.jMenuBar = JMenuBar()
        frame.jMenuBar.add(menu)
        frame.preferredSize = Dimension(768, 768)
        frame.pack()
        frame.setLocationRelativeTo(mainFrame)
        frame.isVisible = true
    }
}