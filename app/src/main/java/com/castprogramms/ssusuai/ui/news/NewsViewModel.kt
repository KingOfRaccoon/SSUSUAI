package com.castprogramms.ssusuai.ui.news

import androidx.lifecycle.ViewModel
import com.castprogramms.ssusuai.repository.firebase.DataUserFirebaseRepository
import com.castprogramms.ssusuai.repository.firebase.NewsFirebaseRepository
import com.castprogramms.ssusuai.tools.New

class NewsViewModel(
    private val dataUserFirebaseRepository: DataUserFirebaseRepository,
    private val newsFirebaseRepository: NewsFirebaseRepository
) : ViewModel() {
    fun getUser() = dataUserFirebaseRepository.person

    fun getAllNews() = newsFirebaseRepository.getAllNews()

    fun addNew(new: New) = newsFirebaseRepository.addNew(new)

    fun addLike(new: New, idUser: String) = newsFirebaseRepository.addLikeToNew(new, idUser)

    fun addSee(new: New, idUser: String) = newsFirebaseRepository.addSeeToNew(new, idUser)
}
