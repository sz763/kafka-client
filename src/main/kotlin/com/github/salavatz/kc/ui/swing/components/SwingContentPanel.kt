package com.github.salavatz.kc.ui.swing.components

import com.github.salavatz.kc.ui.ContentPanel
import com.github.salavatz.kc.ui.MessageConsumerPanel
import com.github.salavatz.kc.ui.MessageSenderPanel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JTabbedPane

@Component
class SwingContentPanel @Autowired constructor(
    senderPanel: MessageSenderPanel, consumerPanel: MessageConsumerPanel
) : JTabbedPane(), ContentPanel {
    init {
        addTab(consumerPanel as JComponent, "Consumer")
        addTab(senderPanel as JComponent, "Producer")
    }

    private fun addTab(pane: JComponent, title: String) {
        this.add(pane)
        this.setTabComponentAt(this.tabCount - 1, tabTitle(title))
    }

    private fun tabTitle(title: String): JLabel {
        val jLabel = JLabel(title)
        jLabel.preferredSize = Dimension(70, 10)
        return jLabel
    }
}