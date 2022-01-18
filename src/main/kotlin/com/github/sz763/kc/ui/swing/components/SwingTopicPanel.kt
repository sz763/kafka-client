package com.github.sz763.kc.ui.swing.components

import com.github.sz763.kc.core.DataFilter
import com.github.sz763.kc.core.DataHandler
import com.github.sz763.kc.core.FavoriteTopics
import com.github.sz763.kc.core.impl.ContainsDataFilter
import com.github.sz763.kc.core.impl.NoopDataFilter
import com.github.sz763.kc.kafka.Context
import com.github.sz763.kc.kafka.KafkaMessageReader
import com.github.sz763.kc.ui.MessageConsumerPanel
import com.github.sz763.kc.ui.TopicPanel
import com.github.sz763.kc.ui.swing.utilities.KeyPressed
import com.github.sz763.kc.ui.swing.utilities.ListModel
import com.github.sz763.kc.ui.swing.utilities.MousePressed
import com.github.weisj.darklaf.components.border.DarkBorders
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Font
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent.BUTTON3
import javax.swing.*
import javax.swing.JOptionPane.YES_NO_OPTION

@Component
class SwingTopicPanel @Autowired constructor(
    private val dataHandler: DataHandler<List<String>, String, String>,
    private val kafkaMessageReader: KafkaMessageReader,
    private val contentPanel: MessageConsumerPanel,
    private val favoriteTopics: FavoriteTopics,
    private val context: Context
) : TopicPanel, SwingPanel("Topics") {
    private val topicModel = ListModel<String>()
    private val favoriteModel = ListModel<String>()
    private val topicList = JList(topicModel)
    private val favoriteList = JList(favoriteModel)
    private val topicContainer = JScrollPane(topicList)
    private val favoriteContainer = JScrollPane(favoriteList)
    private var filter: DataFilter<String> = NoopDataFilter()
    private var filterValue = ""
    private val filterLabel = JLabel()

    init {
        layout = BorderLayout()
        initList()
        initFavoriteList()
        initContainer()
        initLabel()
        topicModel.addAll(dataHandler.values())
        favoriteModel.addAll(favoriteTopics.topics())
        favoriteModel.sort()
    }

    private fun initFavoriteList() {
        favoriteList.addMouseListener(MousePressed { e ->
            val selectedValue = favoriteList.selectedValue
            if (e.clickCount == 2) {
                contentPanel.clear()
                context.topicName(selectedValue)
                kafkaMessageReader.start()
            }
            if (e.button == BUTTON3) {
                val message = "Would you like remove '${selectedValue} from favorite list?"
                val dialog = JOptionPane.showConfirmDialog(
                    this.parent, message, "Remove topic from favorite list", YES_NO_OPTION
                )
                if (dialog == 0) {
                    favoriteModel.removeItem(selectedValue)
                    favoriteTopics.remove(selectedValue)
                    favoriteTopics.save()
                }
            }
        })
    }

    private fun initContainer() {
        topicContainer.horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        topicContainer.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        favoriteContainer.horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        favoriteContainer.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        val pane = JTabbedPane()
        addTab(pane, topicContainer, "All")
        addTab(pane, favoriteContainer, "Favourite")
        this.add(pane, BorderLayout.CENTER)
    }

    private fun addTab(pane: JTabbedPane, jScrollPane: JScrollPane, titile: String) {
        pane.add(jScrollPane)
        pane.setTabComponentAt(pane.tabCount - 1, tabTitle(titile))
    }

    private fun tabTitle(title: String): JLabel {
        val jLabel = JLabel(title)
        jLabel.preferredSize = Dimension(70, 10)
        return jLabel
    }

    private fun initLabel() {
        filterLabel.font = Font(filterLabel.font.name, Font.ITALIC, filterLabel.font.size)
        filterLabel.border = DarkBorders.createBottomBorder()
        add(filterLabel, BorderLayout.PAGE_START)
    }

    private fun initList() {
        topicList.visibleRowCount = -1
        topicList.fixedCellWidth = 300
        topicList.addKeyListener(KeyPressed { e ->
            if (e.isControlDown && e.keyCode == KeyEvent.VK_F) {
                applyFilter()
            }
        })
        topicList.addMouseListener(MousePressed { e ->
            if (e.clickCount == 2) {
                contentPanel.clear()
                context.topicName(topicList.selectedValue)
                kafkaMessageReader.start()
            }
            if (e.button == BUTTON3) {
                val topicName = topicList.selectedValue
                val message = "Would you like to add topic '${topicName}' to favourite list?"
                val dialog =
                    JOptionPane.showConfirmDialog(this.parent, message, "Add topic to favorite list", YES_NO_OPTION)
                if (dialog == 0) {
                    favoriteTopics.add(topicName)
                    favoriteTopics.save()
                    favoriteModel.addElement(topicName)
                    favoriteModel.sort()
                }
            }
        })
    }

    private fun applyFilter() {
        JOptionPane.showInputDialog(this, "Topic name should contain", filterValue)?.let {
            filterLabel.isVisible = it.isNotEmpty()
            filterLabel.text = "Filtered by: $it"
            filterValue = it
            filter = ContainsDataFilter(it)
            val list = dataHandler.filter(filter)
            topicModel.clear()
            topicModel.addAll(list)
        }
    }

    override fun addTopic(name: String) {
        dataHandler.addValue(name)
        if (filter.test(name)) {
            topicModel.addElement(name)
            topicModel.sort()
        }
    }

    override fun addTopics(names: Collection<String>) {

    }
}