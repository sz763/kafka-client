package com.github.svart63.kc.ui.swing.utilities

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

class MousePressed(private val action: (e: MouseEvent) -> Unit) : MouseAdapter() {
    override fun mousePressed(e: MouseEvent?) = action(e!!)
}