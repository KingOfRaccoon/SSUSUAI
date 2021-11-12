package com.castprogramms.ssusuai.users

import com.castprogramms.ssusuai.tools.Event

class CommonUser(
    name: String = "",
    surname: String = "",
    dateOfBirthday: String = "",
    img: String = "",
    val visitedEvents: List<Event> = listOf(),
    idsPublicChat: List<String> = listOf(),
    idsPersonalChat: List<String> = listOf()
) : Person(
    name,
    surname,
    dateOfBirthday,
    img,
    TypeOfPerson.User.name,
    idsPublicChat,
    idsPersonalChat
)