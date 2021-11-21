package com.github.svart63.kc.kafka

class StringTransformer : KafkaTimeBasedTransformer() {

    private fun stringOf(data: Any?): String {
        return when (data) {
            null -> ""
            is String -> data
            is ByteArray -> String(data)
            else -> data.toString()
        }
    }

    override fun convertKey(key: Any?): String = stringOf(key)

    override fun convertValue(value: Any?): String = stringOf(value)
}