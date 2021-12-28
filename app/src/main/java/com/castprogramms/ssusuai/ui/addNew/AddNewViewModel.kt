package com.castprogramms.ssusuai.ui.addNew

import androidx.lifecycle.ViewModel
import com.castprogramms.ssusuai.repository.firebase.NewsFirebaseRepository
import com.castprogramms.ssusuai.tools.New

class AddNewViewModel(private val newsFirebaseRepository: NewsFirebaseRepository): ViewModel() {
    fun addNew(new: New) = newsFirebaseRepository.addNew(new)
}