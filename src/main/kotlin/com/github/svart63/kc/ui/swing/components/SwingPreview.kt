package com.github.svart63.kc.ui.swing.components

import com.github.svart63.kc.core.TextFormatterService
import com.github.svart63.kc.ui.PreviewService
import org.springframework.stereotype.Component
import java.awt.BorderLayout
import java.awt.BorderLayout.CENTER
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JTextArea

@Component
class SwingPreview(private val formatterService: TextFormatterService) : PreviewService {
    override fun show(value: String) {
        val frame = JFrame()
        frame.layout = BorderLayout()
        val area = JTextArea()
        area.text = formatterService.format(value)
        frame.add(area, CENTER)
        frame.preferredSize = Dimension(768, 786)
        frame.pack()
        frame.setLocationRelativeTo(null)
        frame.isVisible = true
        area.componentPopupMenu.add(SwingSaveMenuItem { area.text })
    }
}