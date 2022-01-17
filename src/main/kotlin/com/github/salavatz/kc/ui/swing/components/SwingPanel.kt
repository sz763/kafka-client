package com.github.salavatz.kc.ui.swing.components

import com.github.weisj.darklaf.components.border.DarkBorders
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.JPanel

abstract class SwingPanel(
    title: String,
    top: Int = 1, left: Int = 1, bottom: Int = 1, right: Int = 1
) : JPanel() {
    init {
        layout = BorderLayout()
        border = BorderFactory.createTitledBorder(DarkBorders.createLineBorder(top, left, bottom, right), title)
    }
}