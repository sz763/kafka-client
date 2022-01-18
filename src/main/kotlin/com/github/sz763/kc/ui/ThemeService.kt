package com.github.sz763.kc.ui

interface ThemeService {
    fun themes(): List<String>
    fun setTheme(name: String)
    fun initDefaultTheme()
}