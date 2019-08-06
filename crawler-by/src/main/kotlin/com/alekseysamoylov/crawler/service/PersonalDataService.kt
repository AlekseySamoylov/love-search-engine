package com.alekseysamoylov.crawler.service

import com.alekseysamoylov.crawler.model.Person
import com.alekseysamoylov.crawler.util.SearchUtils.Companion.INTIM_BY_SEARCH_URL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors


@Service
class PersonalDataService(
    @Autowired val linksParcer: LinksParcer,
    @Autowired val pageParser: PageParser
) {

    fun getPersonalData(): List<Person> {
        val personalPages = linksParcer.getLinks(INTIM_BY_SEARCH_URL)
        return personalPages.parallelStream()
            .map { pageParser.getPersonalData(it) }
            .collect(Collectors.toList<Person>())
    }
}
