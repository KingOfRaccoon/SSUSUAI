package com.castprogramms.ssusuai.users

open class Person(
    val name: String = "",
    val surname: String = "",
    val dateOfBirthday: String = "",
    val img: String = "",
    val typeOfPerson: String = TypeOfPerson.User.name,
    val idsPublicChat: List<String> = listOf(),
    val idsPersonalChat: List<String> = listOf()
) {

    fun getFullName() = "$name $surname"

    override fun equals(other: Any?): Boolean {
        return if (other is Person)
            other.getFullName() == getFullName() && other.dateOfBirthday == dateOfBirthday
                    && other.img == img && other.typeOfPerson == typeOfPerson
                    && other.idsPersonalChat == idsPersonalChat
                    && other.idsPublicChat == idsPublicChat
        else
            false
    }
}