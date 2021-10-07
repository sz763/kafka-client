package com.github.svart63.kc.ui.swing.actions

import java.awt.event.MouseEvent
import java.awt.event.MouseListener

class MousePressed(private val action: (e: MouseEvent) -> Unit) : MouseListener {
    override fun mousePressed(e: MouseEvent?) = action(e!!)
    override fun mouseClicked(e: MouseEvent?) {}
    override fun mouseReleased(e: MouseEvent?){}
    override fun mouseEntered(e: MouseEvent?) {}
    override fun mouseExited(e: MouseEvent?) {}
}