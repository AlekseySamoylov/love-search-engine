package com.alekseysamoylov.crawler.service

import com.alekseysamoylov.crawler.service.IntimByGirlPageParser.Companion.PERSONAL_PAGE_URL
import com.alekseysamoylov.crawler.util.SearchUtils.Companion.HREF
import com.alekseysamoylov.crawler.util.SearchUtils.Companion.HREF_SELECTOR
import org.jsoup.Jsoup
import org.springframework.stereotype.Service
import java.util.*
import kotlin.streams.toList


@Service
class IntimByLinksParser: LinksParcer {
    override fun getLinks(url: String): List<String> {
        val document = Jsoup.connect(url).get()
        val linksOnPage = document.select(HREF_SELECTOR)
        val elementList = linksOnPage.stream()
            .filter { e -> e.attributes().get(HREF).contains(PERSONAL_PAGE_URL) }
            .toList()
        val personalPages = ArrayList<String>()
        for (page in elementList) {
            personalPages.add(page.attributes().get(HREF))
        }
        return personalPages
    }
}
