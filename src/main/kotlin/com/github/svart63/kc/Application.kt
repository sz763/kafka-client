package com.github.svart63.kc

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.github.svart63")
open class Application : CommandLineRunner {
    override fun run(vararg args: String?) {
    }
}

fun main() {
    System.setProperty("java.awt.headless", "false");
    SpringApplication.run(Application::class.java, *emptyArray())
}