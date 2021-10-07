package com.github.svart63.kc.ui.swing

import com.github.svart63.kc.core.DataFilter
import com.github.svart63.kc.core.DataHandler
import com.github.svart63.kc.core.impl.ContainsDataFilter
import com.github.svart63.kc.core.impl.NoopDataFilter
import com.github.svart63.kc.kafka.KafkaMessageReader
import com.github.svart63.kc.ui.TopicPanel
import com.github.svart63.kc.ui.swing.utilities.KeyPressed
import com.github.svart63.kc.ui.swing.utilities.ListModel
import com.github.svart63.kc.ui.swing.utilities.MousePressed
import com.github.weisj.darklaf.components.border.DarkBorders
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.awt.BorderLayout
import java.awt.Font
import java.awt.event.KeyEvent
import javax.swing.*

@Component
class SwingTopicPanel @Autowired constructor(
    private val dataHandler: DataHandler<List<String>, String, String>,
    private val kafkaMessageReader: KafkaMessageReader
) : TopicPanel, SwingPanel("Topics") {
    private val topicModel = ListModel<String>()
    private val topicList = JList(topicModel)
    private val container = JScrollPane(topicList)
    private var filter: DataFilter<String> = NoopDataFilter()
    private var filterValue = ""
    private val filterLabel = JLabel()

    init {
        layout = BorderLayout()
        initList()
        initContainer()
        initLabel()
        topicModel.addAll(dataHandler.values())
    }

    private fun initContainer() {
        container.horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        container.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        add(container, BorderLayout.CENTER)
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
                kafkaMessageReader.start(topicList.selectedValue)
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
        }
    }

    override fun addTopics(names: Collection<String>) {

    }
}