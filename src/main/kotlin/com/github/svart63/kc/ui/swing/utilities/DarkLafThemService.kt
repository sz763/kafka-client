package com.github.svart63.kc.ui.swing.utilities

import com.github.svart63.kc.core.Config
import com.github.svart63.kc.ui.ThemeService
import com.github.weisj.darklaf.LafManager
import com.github.weisj.darklaf.theme.*
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class DarkLafThemService(private val config: Config) : ThemeService, InitializingBean {
    private val themes = linkedMapOf(
        Pair("Darcula") { DarculaTheme() },
        Pair("One Dark") { OneDarkTheme() },
        Pair("IntelliJ") { IntelliJTheme() },
        Pair("Solarized Dark") { SolarizedDarkTheme() },
        Pair("Solarized Light") { SolarizedLightTheme() },
        Pair("High Contrast Dark") { HighContrastDarkTheme() },
        Pair("High Contrast Light") { HighContrastLightTheme() },
    )

    override fun themes(): List<String> = themes.keys.toList().sortedDescending()

    override fun setTheme(name: String) {
        LafManager.installTheme(themes[name]!!())
        config.updateTheme(name)
    }

    override fun initDefaultTheme() {
        setTheme(config.defaultTheme())
    }

    override fun afterPropertiesSet() {
        initDefaultTheme()
    }
}