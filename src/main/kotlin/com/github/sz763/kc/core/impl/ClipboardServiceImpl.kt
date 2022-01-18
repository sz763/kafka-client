package com.github.sz763.kc.core.impl

import com.github.sz763.kc.ui.ClipboardService
import org.springframework.stereotype.Service
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

@Service
class ClipboardServiceImpl : ClipboardService {
    override fun copy(text: String) {
        val selection = StringSelection(text)
        Toolkit.getDefaultToolkit().systemClipboard.setContents(selection, null)
    }
}