package com.castprogramms.ssusuai.users

open class Person(
    val name: String = "",
    val surname: String = "",
    val dateOfBirthday: String = "",
    val img: String = "",
    val typeOfPerson: String = ""
){

    fun getFullName() = "$name $surname"
}