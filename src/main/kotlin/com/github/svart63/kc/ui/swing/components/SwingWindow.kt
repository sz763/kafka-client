package com.github.svart63.kc.ui.swing.components

import com.github.svart63.kc.core.Config
import com.github.svart63.kc.ui.ContentPanel
import com.github.svart63.kc.ui.MainWindow
import com.github.svart63.kc.ui.ThemeService
import com.github.svart63.kc.ui.TopicPanel
import com.github.svart63.kc.ui.swing.utilities.MousePressed
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import java.awt.BorderLayout
import java.awt.Container
import java.awt.Dimension
import javax.swing.*
import javax.swing.border.EmptyBorder

@Component
class SwingWindow @Autowired constructor(
    private val themeService: ThemeService,
    private val topicPanel: TopicPanel,
    private val contentPanel: ContentPanel,
    private val config: Config,
) : MainWindow, ApplicationListener<ContextRefreshedEvent> {
    private val mainFrame = JFrame("Kafka-Client")
    private val mainContainer = JPanel()


    init {
        initMain()
        addMenu()
        addPanels()
    }

    override fun show() {
        mainFrame.pack()
        mainFrame.setLocationRelativeTo(null)
        mainFrame.isVisible = true
    }

    private fun addPanels() {
        val splitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, topicPanel as Container, contentPanel as Container)
        mainContainer.add(splitPane)
    }

    private fun initMain() {
        val dimension = Dimension(1024, 768)
        mainContainer.layout = BorderLayout()
        mainContainer.border = EmptyBorder(2, 2, 2, 2)
        mainContainer.preferredSize = dimension
        mainContainer.repaint()

        mainFrame.contentPane = mainContainer
        mainFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        mainFrame.preferredSize = dimension
    }

    private fun addMenu() {
        mainFrame.jMenuBar = JMenuBar()
        themMenu()
        settingsMenu()
    }

    private fun settingsMenu() {
        val menu = JMenu("Settings")
        val item = JMenuItem("Save")
        item.addMouseListener(MousePressed { config.save() })
        menu.add(item)
        mainFrame.jMenuBar.add(menu)
    }

    private fun themMenu() {
        val menu = JMenu("Themes")
        mainFrame.jMenuBar.add(menu)
        themeService.themes().forEach { name ->
            val item = JMenuItem(name)
            menu.add(item)
            item.addMouseListener(MousePressed { themeService.setTheme(name) })
        }
    }

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        show()
    }
}