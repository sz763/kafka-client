package com.github.svart63.kc.core

interface FavoriteTopics {
    fun add(name: String)
    fun save()
    fun remove(name: String)
    fun topics(): Collection<String>
    fun load()
}