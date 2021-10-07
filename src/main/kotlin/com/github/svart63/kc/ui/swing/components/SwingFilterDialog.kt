package com.github.svart63.kc.ui.swing.components

import javax.swing.JDialog

class SwingFilterDialog(title: String) : JDialog() {
    init {
        isModal = true
        this.title = title
    }
}