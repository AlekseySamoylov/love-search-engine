package com.alekseysamoylov.crawler.service

import com.alekseysamoylov.crawler.model.Person
import com.alekseysamoylov.crawler.util.SearchUtils.Companion.INTIM_BY_SEARCH_URL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.concurrent.CopyOnWriteArrayList
import java.util.stream.Collectors


@Service
class PersonalDataService(
    @Autowired val linksParcer: LinksParcer,
    @Autowired val pageParser: PageParser
) {

    companion object {
        private var girlsListCached = CopyOnWriteArrayList<Person>()
    }

    fun getPersonalData(): List<Person> {
        if (girlsListCached.isEmpty()) {
            fillGirlsList()
        }
        return girlsListCached
    }

    @Scheduled(cron = "0 0/15 * 1/1 * ? *")
    fun fillGirlsList() {
        val personalPages = linksParcer.getLinks(INTIM_BY_SEARCH_URL)
        val newGirls = personalPages.parallelStream()
            .map { pageParser.getPersonalData(it) }
            .collect(Collectors.toList<Person>())
        girlsListCached.clear()
        girlsListCached.addAll(newGirls)
    }

}
