package com.github.salavatz.kc

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["com.github.salavatz"])
open class Application : CommandLineRunner {
    override fun run(vararg args: String?) {
    }
}

fun main() {
    System.setProperty("java.awt.headless", "false")
    SpringApplication.run(Application::class.java, *emptyArray())
}