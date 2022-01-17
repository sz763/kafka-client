package com.github.salavatz.kc.ui

interface ThemeService {
    fun themes(): List<String>
    fun setTheme(name: String)
    fun initDefaultTheme()
}