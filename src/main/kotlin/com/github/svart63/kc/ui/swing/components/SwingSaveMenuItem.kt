package com.github.svart63.kc.ui.swing.components

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.io.File
import javax.swing.JFileChooser
import javax.swing.JMenuItem
import javax.swing.filechooser.FileNameExtensionFilter

class SwingSaveMenuItem(action: () -> String) : JMenuItem("Save") {
    private val fileChooser = JFileChooser()
    private val regex = Regex(".+\\.\\w+\$")

    init {
        addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent?) {
                fileChooser.fileFilter =
                    FileNameExtensionFilter("*.txt,*.json", "txt", "json")
                fileChooser.isMultiSelectionEnabled = false
                fileChooser.showSaveDialog(null)
                val file = fileChooser.selectedFile
                file?.let { f ->
                    if (!f.name.matches(regex)) {
                        val pathname = f.absolutePath + ".txt"
                        File(pathname).writeText(action())
                    } else {
                        f.writeText(action())
                    }
                }

            }
        })
    }
}