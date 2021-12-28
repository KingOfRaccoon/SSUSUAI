package com.castprogramms.ssusuai.ui.registration

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.repository.firebase.DataUserFirebaseRepository
import com.castprogramms.ssusuai.users.Admin
import com.castprogramms.ssusuai.users.Person

class RegistrationViewModel(private val dataUserFirebaseRepository: DataUserFirebaseRepository) :
    ViewModel() {
    val lifeDataLoadPhoto = MutableLiveData<Resource<Uri>>()
    private val codeAdmin = "5000"
    private var birthday = ""
    private var name = ""
    private var surname = ""
    val googleAccount = dataUserFirebaseRepository.getGoogleSignAccount()
    fun setBirthday(date: String) {
        birthday = date
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setSurname(surname: String) {
        this.surname = surname
    }

    fun createPerson(idPerson: String, img: String, pin: String, isAdmin: Boolean) =
        dataUserFirebaseRepository.addPerson(idPerson,
            if (codeAdmin == pin && isAdmin)
                Admin(name, surname, birthday, img)
            else
                Person(name, surname, birthday, img)
        )

    fun loadPhotoUser(uri: Uri, userId: String) {
        dataUserFirebaseRepository.loadPhotoUserInRegistration(uri, userId).observeForever {
            lifeDataLoadPhoto.postValue(it)
        }
    }
}