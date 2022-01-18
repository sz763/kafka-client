package com.github.sz763.kc.ui.swing.components

import com.github.sz763.kc.core.Config
import com.github.sz763.kc.kafka.Context
import com.github.sz763.kc.kafka.MessageSender
import com.github.sz763.kc.ui.MessageSenderPanel
import com.github.sz763.kc.ui.swing.utilities.MousePressed
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import java.awt.BorderLayout
import javax.swing.*

@Component
class SwingMessageSenderPanel constructor(
    private val messageSender: MessageSender,
    private val context: Context,
    private val config: Config
) : SwingPanel(""), MessageSenderPanel, InitializingBean {
    private val keyInput = JTextField()
    private val sendButton = JButton("Send")
    private val messageBody = JTextArea()

    private fun initBody() {
        val body = JPanel(BorderLayout())
        body.add(JLabel("Body"), BorderLayout.PAGE_START)
        body.add(messageBody, BorderLayout.CENTER)
        this.add(body, BorderLayout.CENTER)
    }

    private fun initHeader() {
        val header = JPanel(BorderLayout())
        val keyLabel = JLabel("Key")
        header.add(keyLabel, BorderLayout.PAGE_START)
        header.add(keyInput, BorderLayout.CENTER)
        header.add(sendButton, BorderLayout.EAST)
        this.add(header, BorderLayout.PAGE_START)
    }

    override fun afterPropertiesSet() {
        initHeader()
        initBody()
        sendButton.addMouseListener(MousePressed {
            if (it.button == 1) {
                if (context.topicName() == "") {
                    JOptionPane.showConfirmDialog(this, "Topic is not selected, please select topic.")
                    return@MousePressed
                }
                messageSender.sendMessage(
                    context.topicName(),
                    keyInput.text,
                    messageBody.text,
                    config.valueSerde()
                )
            }
        })
    }
}