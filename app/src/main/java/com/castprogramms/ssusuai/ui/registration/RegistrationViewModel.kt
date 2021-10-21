package com.castprogramms.ssusuai.ui.registration

import androidx.lifecycle.ViewModel
import com.castprogramms.ssusuai.repository.firebase.DataUserFirebaseRepository
import com.castprogramms.ssusuai.users.Person

class RegistrationViewModel(private val dataUserFirebaseRepository: DataUserFirebaseRepository) :
    ViewModel() {
    private var birthday = ""
    private var name = ""
    private var surname = ""

    fun setBirthday(date: String) {
        birthday = date
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setSurname(surname: String) {
        this.surname = surname
    }

    fun createPerson(idPerson: String, img: String) =
        dataUserFirebaseRepository.addPerson(idPerson, Person(name, surname, birthday, img))
}