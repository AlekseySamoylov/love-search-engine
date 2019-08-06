package com.alekseysamoylov.crawler.model


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
