package com.github.svart63.kc.kafka

import com.github.svart63.kc.core.Config
import org.apache.kafka.common.serialization.Serde
import org.reflections.Reflections
import org.reflections.util.ConfigurationBuilder
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ReflectionSerdeProvider @Autowired constructor(private val config: Config) : SerdeProvider, InitializingBean {
    //TODO make it lazy
    private lateinit var serdes: Map<String, Serde<*>>
    private lateinit var transformers: Map<String, KafkaTimeBasedTransformer>

    override fun serdeList(): List<String> = ArrayList(serdes.keys).sorted()

    override fun keySerde(): Serde<*> = serdeOf(config.keySerde())

    override fun valueSerde(): Serde<*> = serdeOf(config.valueSerde())

    override fun transformer(): KafkaTimeBasedTransformer {
        val transformerClassName = config.serdeTransformer()
        return (transformers[transformerClassName] ?: throw IllegalArgumentException(
            "Undefined serde transformer $transformerClassName name, " +
                    "please use one of the following: [${transformers.keys}]"
        ))
    }

    override fun serdeOf(serdeName: String): Serde<*> {
        return serdes[serdeName]
            ?: throw IllegalArgumentException(
                "Undefined serde $serdeName name, please use one of the following: [${this.serdes.keys}]"
            )

    }

    override fun afterPropertiesSet() {
        val builder = ConfigurationBuilder().forPackages(*config.serdePackages())
        val reflections = Reflections(builder)
        initSerdes(reflections)
        initTransformers(reflections)
    }

    private fun initTransformers(reflections: Reflections) {
        val transformers = subtypesWithDefaultConstructors(reflections, KafkaTimeBasedTransformer::class.java)
        this.transformers = transformers.associate { Pair(it.name, it.getDeclaredConstructor().newInstance()) }
    }

    private fun initSerdes(reflections: Reflections) {
        val serdes = subtypesWithDefaultConstructors(reflections, Serde::class.java)
        this.serdes = serdes.associate { Pair(it.simpleName, it.getDeclaredConstructor().newInstance()) }
    }

    private fun <T> subtypesWithDefaultConstructors(reflections: Reflections, clazz: Class<T>) =
        reflections.getSubTypesOf(clazz)
            .filter { it.constructors.any { c -> c.parameterCount == 0 } }
}