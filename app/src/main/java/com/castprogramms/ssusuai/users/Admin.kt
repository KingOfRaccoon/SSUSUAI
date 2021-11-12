package com.castprogramms.ssusuai.users

class Admin(
    name: String = "",
    surname: String = "",
    dateOfBirthday: String = "",
    img: String = "",
    idsPublicChat: List<String> = listOf(),
    idsPersonalChat: List<String> = listOf()
) : Person(
    name,
    surname,
    dateOfBirthday,
    img,
    TypeOfPerson.Admin.name,
    idsPublicChat,
    idsPersonalChat
)