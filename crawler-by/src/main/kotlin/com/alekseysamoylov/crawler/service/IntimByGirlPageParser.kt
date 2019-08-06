package com.alekseysamoylov.crawler.service

import com.alekseysamoylov.crawler.model.Person
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.springframework.stereotype.Service


@Service
class IntimByGirlPageParser : PageParser {
    companion object {
        const val DOMAIN_URL = "http://in.timby.net"
        const val PERSONAL_PAGE_URL = "/cgi-bin/viewad.pl?id="
        private const val WAS_SEEN = "Анкета была просмотрена"
        private val PRICE_REGEX = """(\$\d*)(\d*)""".toRegex()
    }

    override fun getPersonalData(url: String): Person {
        val document = Jsoup.connect(DOMAIN_URL + url).get()
        val girlParametersList = document.select("table tbody tr td table tbody tr")
        val parameterMap: Map<String, String> = girlParametersList
            .associateBy(
                { getKeyParam(it) },
                { getValueParam(it) }
            )
        val person = Person()
        person.name = parameterMap["Имя:"].orEmpty()
        person.age = parameterMap["Возраст:"].orEmpty()
        person.email = parameterMap["E-mail:"].orEmpty()
        person.phone = parameterMap["Телефон:"].orEmpty()
        person.height = parameterMap["Рост:"].orEmpty()
        person.weight = parameterMap["Вес:"].orEmpty()
        person.publicationDate = parameterMap["Дата публикации:"].orEmpty()
        person.seeCount = parameterMap[WAS_SEEN].orEmpty()
        person.message = parameterMap[""].orEmpty()
        try {
            person.price = PRICE_REGEX.find(parameterMap[""].orEmpty())!!.value
        } catch (ex: NullPointerException) {
            println("Price not found")
        }
        val girlPhotosList = document.select("center img[src]")
        if (girlParametersList != null && girlParametersList.size > 0) {
            person.photos = girlPhotosList.map { DOMAIN_URL + it.attributes().get("SRC") }
        }
        println(person)
        return person
    }

    private fun getKeyParam(element: Element): String {
        return when {
            element.children().size > 1 -> element.child(0).text()
            element.child(0).text().contains(WAS_SEEN) -> WAS_SEEN
            else -> ""
        }
    }

    private fun getValueParam(element: Element): String {
        return if (element.children().size > 1) {
            element.child(1).text()
        } else {
            element.child(0).text()
        }
    }
}
