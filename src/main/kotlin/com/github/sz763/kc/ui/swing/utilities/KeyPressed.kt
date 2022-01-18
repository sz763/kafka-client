package com.github.sz763.kc.ui.swing.utilities

import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

class KeyPressed(private val action: (e: KeyEvent) -> Unit) : KeyAdapter() {
    override fun keyPressed(e: KeyEvent?) = action(e!!)
}