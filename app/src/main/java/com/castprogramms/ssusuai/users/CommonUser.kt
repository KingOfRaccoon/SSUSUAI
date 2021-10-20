package com.castprogramms.ssusuai.users

class CommonUser(
    name: String = "",
    surname: String = "",
    dateOfBirthday: String = "",
    img: String = ""
) : Person(name, surname, dateOfBirthday, img, TypeOfPerson.User.name)