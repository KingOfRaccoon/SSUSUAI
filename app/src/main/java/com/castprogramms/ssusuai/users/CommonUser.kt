package com.castprogramms.ssusuai.users

import com.castprogramms.ssusuai.tools.Event

class CommonUser(
    name: String = "",
    surname: String = "",
    dateOfBirthday: String = "",
    img: String = "",
    val visitedEvents: List<Event> = listOf()
) : Person(name, surname, dateOfBirthday, img, TypeOfPerson.User.name)