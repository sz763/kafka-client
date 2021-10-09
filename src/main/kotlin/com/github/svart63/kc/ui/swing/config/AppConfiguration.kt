package com.github.svart63.kc.ui.swing.config

import com.github.svart63.kc.core.DataHandler
import com.github.svart63.kc.core.TableDataHandler
import com.github.svart63.kc.core.impl.ListDataHandler
import com.github.svart63.kc.core.impl.VectorDataHandler
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.Vector

@Configuration
class AppConfiguration {
    @Bean
    @Qualifier("data_handler_table")
    fun tableDataHandler(): TableDataHandler<Vector<Vector<String>>, String, Vector<String>> {
        return VectorDataHandler()
    }

    @Bean
    @Qualifier("data_handler_topic")
    fun topicDataHandler(): DataHandler<List<String>, String, String> {
        return ListDataHandler()
    }
}