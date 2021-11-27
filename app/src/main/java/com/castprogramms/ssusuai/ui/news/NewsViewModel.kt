package com.castprogramms.ssusuai.ui.news

import androidx.lifecycle.ViewModel
import com.castprogramms.ssusuai.repository.firebase.DataUserFirebaseRepository

class NewsViewModel(
    private val dataUserFirebaseRepository: DataUserFirebaseRepository
) : ViewModel() {
    fun getUser(id: String) = dataUserFirebaseRepository.getPerson(id)
}
