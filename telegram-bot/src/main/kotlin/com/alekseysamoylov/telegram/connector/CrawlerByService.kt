package com.alekseysamoylov.telegram.connector

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class CrawlerByService {

    companion object {
        val userRequestCounterMap = mutableMapOf<Int, Int>()
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

    fun getPersons(userId: Int, offset: Int?): String {
        val restTemplate = RestTemplate()
        val persons = restTemplate.getForEntity("http://localhost:8086/persons", Array<Person>::class.java).body!!
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
    var pageLink: String = "",
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
        return "Person(pageLink='$pageLink', name='$name', age='$age', email='$email', " +
                "phone='$phone', publicationDate='$publicationDate', seeCount='$seeCount', " +
                "height='$height', weight='$weight', price='$price', message='$message', photos=$photos)"
    }
}
