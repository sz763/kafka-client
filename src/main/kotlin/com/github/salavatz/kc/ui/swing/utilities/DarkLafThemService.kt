package com.github.salavatz.kc.ui.swing.utilities

import com.github.salavatz.kc.core.Config
import com.github.salavatz.kc.ui.ThemeService
import com.github.weisj.darklaf.LafManager
import com.github.weisj.darklaf.theme.DarculaTheme
import com.github.weisj.darklaf.theme.HighContrastDarkTheme
import com.github.weisj.darklaf.theme.HighContrastLightTheme
import com.github.weisj.darklaf.theme.IntelliJTheme
import com.github.weisj.darklaf.theme.OneDarkTheme
import com.github.weisj.darklaf.theme.SolarizedDarkTheme
import com.github.weisj.darklaf.theme.SolarizedLightTheme
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