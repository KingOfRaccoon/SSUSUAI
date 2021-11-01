package com.castprogramms.ssusuai.users

class Admin(
    name: String = "",
    surname: String = "",
    dateOfBirthday: String = "",
    img: String = "",
    chats: List<String> = listOf()
) : Person(name, surname, dateOfBirthday, img, chats, TypeOfPerson.Admin.name)