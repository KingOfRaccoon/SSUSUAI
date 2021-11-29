package com.castprogramms.ssusuai.ui.editProfile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.repository.firebase.DataUserFirebaseRepository

class EditProfileViewModel (private val dataUserFirebaseRepository: DataUserFirebaseRepository
) : ViewModel() {
    fun getCommonUser() = dataUserFirebaseRepository.commonUser
    fun getAdmin() = dataUserFirebaseRepository.admin

    var _name : String= ""
    var _surname : String = ""

    val lifeDataLoadPhoto = MutableLiveData<Resource<String>>()

//TODO починить обновление данных

//    fun updateUserFirstName(name: String, id: String) = dataUserFirebaseRepository.editNameStudent(name, id)
//    fun updateUserSecondName(lastName: String, id: String) = dataUserFirebaseRepository.editLastNameStudent(lastName, id)

//    fun loadPhotoUser(uri: Uri, userId: String) {
//        dataUserFirebaseRepository.loadPhotoUser(uri, userId).observeForever {
//            lifeDataLoadPhoto.postValue(it)
//        }
//    }
}
