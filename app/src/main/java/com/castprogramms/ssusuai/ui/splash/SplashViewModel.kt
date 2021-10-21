package com.castprogramms.ssusuai.ui.splash

import androidx.lifecycle.ViewModel
import com.castprogramms.ssusuai.repository.firebase.DataUserFirebaseRepository

class SplashViewModel(private val dataUserFirebaseRepository: DataUserFirebaseRepository): ViewModel() {
    fun getUser(userId: String) = dataUserFirebaseRepository.getPerson(userId)
}