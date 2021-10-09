package com.github.svart63.kc.ui.swing.utilities

import com.github.svart63.kc.core.Config
import com.github.svart63.kc.ui.ThemeService
import com.github.weisj.darklaf.LafManager
import com.github.weisj.darklaf.theme.*
import org.springframework.stereotype.Component

@Component
class DarkLafThemService(private val config: Config) : ThemeService {
    private val themes = linkedMapOf(
        Pair("Darcula") { DarculaTheme() },
        Pair("One Dark") { OneDarkTheme() },
        Pair("IntelliJ") { IntelliJTheme() },
        Pair("Solarized Dark") { SolarizedDarkTheme() },
        Pair("Solarized Light") { SolarizedLightTheme() },
        Pair("High Contrast Dark") { HighContrastDarkTheme() },
        Pair("High Contrast Light") { HighContrastLightTheme() },
    )

    override fun themes(): List<String> = themes.keys.toList().sorted()

    override fun setTheme(name: String) {
        setThemeByName(name)
        config.updateTheme(name)
    }

    private fun setThemeByName(name: String) {
        LafManager.installTheme(themes[name]!!())
    }

    override fun initDefaultTheme() {
        setThemeByName(config.defaultTheme())
    }

}