package com.alekseysamoylov.telegram

import org.springframework.boot.SpringApplication
import org.telegram.telegrambots.ApiContextInitializer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.PropertySource


@SpringBootApplication
@PropertySource("classpath:application.properties")
class YourApplicationMainClass

fun main(args: Array<String>) {
    ApiContextInitializer.init()

    SpringApplication.run(YourApplicationMainClass::class.java, *args)
}
