package com.alekseysamoylov.telegram

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.PropertySource
import org.telegram.telegrambots.ApiContextInitializer


@SpringBootApplication
@PropertySource("classpath:application.properties")
class YourApplicationMainClass

fun main(args: Array<String>) {
    ApiContextInitializer.init()

    SpringApplication.run(YourApplicationMainClass::class.java, *args)
}
