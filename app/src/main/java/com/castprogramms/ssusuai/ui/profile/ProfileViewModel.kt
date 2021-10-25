package com.castprogramms.ssusuai.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.castprogramms.ssusuai.SuaiApplication
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.repository.firebase.DataUserFirebaseRepository
import com.castprogramms.ssusuai.tools.NeedTools.lifecycleOwner
import com.castprogramms.ssusuai.users.Person
import com.google.android.gms.auth.api.signin.GoogleSignIn

class ProfileViewModel(
    private val dataUserFirebaseRepository: DataUserFirebaseRepository
) : ViewModel() {
    fun getUser(userId: String) = dataUserFirebaseRepository.getPerson(userId)
}
