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
open class AppConfiguration {
    @Bean
    @Qualifier("data_handler_table")
    open fun tableDataHandler(): TableDataHandler<Vector<Vector<String>>, String, Vector<String>> {
        return VectorDataHandler()
        /*val handler = VectorDataHandler()
        for (i in 0..150) {
            val v = Vector<String>()
            v.add(UUID.randomUUID().toString())
            v.add("{\"value#$i\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"}")
            handler.addValue(v)
        }
        return handler*/
    }

    @Bean
    @Qualifier("data_handler_topic")
    open fun topicDataHandler(): DataHandler<List<String>, String, String> {
        return ListDataHandler()
/*        val dataHandler = ListDataHandler()
        for (i in 0..150) {
            dataHandler.addValue("topic #$i")
        }
        return dataHandler*/
    }
}