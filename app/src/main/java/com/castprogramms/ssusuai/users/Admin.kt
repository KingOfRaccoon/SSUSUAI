package com.castprogramms.ssusuai.users

class Admin(
    name: String = "",
    surname: String = "",
    dateOfBirthday: String = "",
    img: String = ""
) : Person(name, surname, dateOfBirthday, img, TypeOfPerson.Admin.name)