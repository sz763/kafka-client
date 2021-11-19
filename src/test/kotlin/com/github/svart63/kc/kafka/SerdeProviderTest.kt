package com.github.svart63.kc.kafka

import com.github.svart63.kc.core.Config
import io.mockk.every
import io.mockk.mockkClass
import org.apache.kafka.common.serialization.Serdes
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test

internal class SerdeProviderTest {
    private val config = mockkClass(Config::class)

    @Test
    internal fun testProvideKeySerde() {
        every { config.keySerde() }.returns("StringSerde")
        every { config.serdePackages() }.returns(arrayOf("org.apache.kafka.common.serialization"))
        val provider = SerdeProvider(config)
        provider.afterPropertiesSet()
        assertThat(provider.keySerde()).isInstanceOf(Serdes.StringSerde::class.java)
    }

    @Test
    internal fun testProvideValueSerde() {
        every { config.valueSerde() }.returns("ByteArraySerde")
        every { config.serdePackages() }.returns(arrayOf("org.apache.kafka.common.serialization"))
        val provider = SerdeProvider(config)
        provider.afterPropertiesSet()
        assertThat(provider.valueSerde()).isInstanceOf(Serdes.ByteArray()::class.java)
    }

    @Test
    internal fun testProvideTransformer() {
        every { config.serdeTransformer() }.returns("com.github.svart63.kc.kafka.StringTransformer")
        every { config.serdePackages() }.returns(arrayOf("com.github.svart63.kc.kafka"))
        val provider = SerdeProvider(config)
        provider.afterPropertiesSet()
        assertThat(provider.transformer()).isInstanceOf(StringTransformer::class.java)
    }

    @Test
    internal fun testKeyProviderThrowsErrorIfNotDefined() {
        every { config.keySerde() }.returns("JsonSerde")
        every { config.serdePackages() }.returns(arrayOf("org.apache.kafka.common.serialization"))
        val provider = SerdeProvider(config)
        provider.afterPropertiesSet()
        assertThatIllegalArgumentException().isThrownBy { provider.keySerde() }
            .withMessageContaining("StringSerde")
    }
}