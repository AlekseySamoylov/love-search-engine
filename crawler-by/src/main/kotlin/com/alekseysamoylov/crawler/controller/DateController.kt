package com.alekseysamoylov.crawler.controller

import com.alekseysamoylov.crawler.model.Person
import com.alekseysamoylov.crawler.service.PersonalDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class DateController(@Autowired val personalDataService: PersonalDataService) {

    @RequestMapping("/")
    fun index(): String {
        return "Greetings from Spring Boot!"
    }

    @RequestMapping("/persons")
    fun persons(): List<Person> {
        return personalDataService.getPersonalData()
    }
}
