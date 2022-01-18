package com.github.sz763.kc.ui.swing.components

import javax.swing.JDialog

class SwingFilterDialog(title: String) : JDialog() {
    init {
        isModal = true
        this.title = title
    }
}