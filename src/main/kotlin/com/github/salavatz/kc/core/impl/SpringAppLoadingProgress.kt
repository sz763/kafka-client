package com.github.salavatz.kc.core.impl

import com.github.salavatz.kc.core.LoadingProgress
import com.github.salavatz.kc.ui.SplashScreen
import com.github.salavatz.kc.ui.ThemeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Service

@Service
class SpringAppLoadingProgress @Autowired constructor(
    themeService: ThemeService,
    private val splashScreen: SplashScreen,
    applicationContext: ApplicationContext
) :
    ApplicationListener<ContextRefreshedEvent>, LoadingProgress, BeanPostProcessor {
    init {
        themeService.initDefaultTheme()
        splashScreen.progressMaxValue(applicationContext.beanDefinitionCount)
        splashScreen.showSplashScreen()
    }

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        splashScreen.hideSplashScreen()
    }

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        splashScreen.moduleLoading(bean.javaClass.simpleName)
        return super.postProcessBeforeInitialization(bean, beanName)
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        splashScreen.updateProgress(1)
        return super.postProcessAfterInitialization(bean, beanName)
    }
}