package com.github.salavatz.kc.ui

interface SplashScreen {
    fun showSplashScreen()
    fun hideSplashScreen()
    fun moduleLoading(name: String)
    fun updateProgress(item: Int)
    fun progressMaxValue(count: Int)
}