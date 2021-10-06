package com.github.svart63.kc.ui

interface ThemeService {
    fun themes(): List<String>
    fun setTheme(name: String)
}