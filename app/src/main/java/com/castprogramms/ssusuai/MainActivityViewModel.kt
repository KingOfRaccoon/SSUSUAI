package com.castprogramms.ssusuai

import androidx.lifecycle.ViewModel
import com.castprogramms.ssusuai.repository.firebase.DataUserFirebaseRepository

class MainActivityViewModel(private val dataUserFirebaseRepository: DataUserFirebaseRepository): ViewModel() {
    fun getUser(userId: String) = dataUserFirebaseRepository.getPerson(userId)
}