package com.alekseysamoylov.telegram.connector

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.util.*


@Service
class CrawlerByService {

    companion object {
        val userRequestCounterMap = mutableMapOf<Int, Int>()
        var resultCache = emptyArray<Person>()
        var lastUpdateDate = LocalDateTime.now()
    }

    private fun getCounterForUser(userId: Int): Int {
        var result = userRequestCounterMap.get(userId)
        if (result == null) {
            result = 0
            userRequestCounterMap[userId] = result
        } else {
            userRequestCounterMap[userId] = ++result
        }
        return result
    }

    private fun resetCounter(userId: Int) {
        userRequestCounterMap[userId] = 0
    }

    private fun getCachedPersons(): Array<Person> {
        if (lastUpdateDate.plusMinutes(5).isBefore(LocalDateTime.now()) || resultCache.isEmpty()) {
            lastUpdateDate = LocalDateTime.now()
            val restTemplate = RestTemplate();
            resultCache = restTemplate.getForEntity("http://localhost:8086/persons", Array<Person>::class.java).body!!
            return resultCache
        } else {
            return resultCache
        }
    }

    fun getPersons(userId: Int, offset: Int?): String {
        val persons = getCachedPersons()
        if (offset != null) {
           return persons[offset].toString()
        }
        val nextForUser = getCounterForUser(userId)
        if (persons.size >= nextForUser) {
            return persons[nextForUser].toString()
        } else {
            resetCounter(userId)
            return persons[0].toString()
        }
    }
}

class Person(
    var name: String = "",
    var age: String = "",
    var email: String = "",
    var phone: String = "",
    var publicationDate: String = "",
    var seeCount: String = "",
    var height: String = "",
    var weight: String = "",
    var price: String = "",
    var message: String = "",
    var photos: List<String> = emptyList()
) {
    override fun toString(): String {
        return "Person(name='$name', age='$age', email='$email', " +
                "phone='$phone', publicationDate='$publicationDate', seeCount='$seeCount', " +
                "height='$height', weight='$weight', price='$price', message='$message', photos=$photos)"
    }
}
