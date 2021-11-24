package com.github.svart63.kc.ui.swing.components

import com.github.svart63.kc.ui.ContentPanel
import com.github.svart63.kc.ui.MessageConsumerPanel
import com.github.svart63.kc.ui.MessageSenderPanel
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
        addTab(senderPanel as JComponent, "Sender")
    }

    private fun addTab(pane: JComponent, titile: String) {
        this.add(pane)
        this.setTabComponentAt(this.tabCount - 1, tabTitle(titile))
    }

    private fun tabTitle(title: String): JLabel {
        val jLabel = JLabel(title)
        jLabel.preferredSize = Dimension(70, 10)
        return jLabel
    }
}