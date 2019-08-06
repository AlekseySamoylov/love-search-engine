package com.alekseysamoylov.crawler.service

import com.alekseysamoylov.crawler.model.Person


interface PageParser {
    fun getPersonalData(url: String): Person
}
