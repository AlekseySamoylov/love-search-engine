package com.alekseysamoylov.crawler.service

import org.w3c.dom.Element


interface LinksParcer {
    fun getLinks(url: String): List<String>
}
