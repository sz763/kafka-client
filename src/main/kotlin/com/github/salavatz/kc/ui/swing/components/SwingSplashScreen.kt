package com.github.salavatz.kc.ui.swing.components

import com.github.salavatz.kc.ui.SplashScreen
import org.springframework.stereotype.Component
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JProgressBar
import javax.swing.border.EmptyBorder

@Component
class SwingSplashScreen : JFrame(), SplashScreen {
    private val progress = JProgressBar()
    private val label = JLabel()
    private val container = JPanel()

    init {
        initContainer()
        this.layout = BorderLayout()
        this.isResizable = false
        this.isUndecorated = true
        this.add(container, BorderLayout.CENTER)
        this.preferredSize = Dimension(300, 100)
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.pack()
    }

    private fun initContainer() {
        container.layout = BorderLayout()
        container.add(JLabel("Loading modules..."), BorderLayout.PAGE_START)
        container.add(progress, BorderLayout.PAGE_END)
        container.add(label, BorderLayout.CENTER)
        container.border = EmptyBorder(10, 10, 10, 10)
    }

    override fun showSplashScreen() {
        this.setLocationRelativeTo(null)
        this.isVisible = true
    }

    override fun hideSplashScreen() {
        this.isVisible = false
    }

    override fun moduleLoading(name: String) {
        label.text = "Loading module: $name"
    }

    override fun updateProgress(item: Int) {
        progress.value += item
    }

    override fun progressMaxValue(count: Int) {
        progress.maximum = count
    }
}