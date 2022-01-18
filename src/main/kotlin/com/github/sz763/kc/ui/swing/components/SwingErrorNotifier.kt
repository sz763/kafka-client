package com.github.sz763.kc.ui.swing.components

import com.github.sz763.kc.ui.ErrorNotifier
import org.springframework.stereotype.Service
import java.awt.Dimension
import javax.swing.JDialog
import javax.swing.JTextArea

@Service
class SwingErrorNotifier : ErrorNotifier {

    override fun notify(errorMessage: String) {
        val dialog = JDialog()
        dialog.isModal = true
        dialog.title = "Unexpected error occurred"
        val area = JTextArea()
        area.text = errorMessage
        dialog.add(area)
        dialog.preferredSize = Dimension(500, 300)
        dialog.pack()
        dialog.setLocationRelativeTo(null)
        dialog.isVisible = true
    }

}