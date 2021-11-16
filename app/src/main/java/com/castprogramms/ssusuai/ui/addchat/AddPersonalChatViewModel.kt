package com.castprogramms.ssusuai.ui.addchat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.repository.firebase.ChatsFirebaseRepository
import com.castprogramms.ssusuai.repository.firebase.DataUserFirebaseRepository
import com.castprogramms.ssusuai.users.Person

class AddPersonalChatViewModel(
    private val dataUserFirebaseRepository: DataUserFirebaseRepository,
    private val chatsFirebaseRepository: ChatsFirebaseRepository
) : ViewModel() {
    val usersLiveData = MutableLiveData<Resource<List<Pair<String, Person>>>>()

    fun getUsers(person: Person, id: String) {
        dataUserFirebaseRepository.getPersonWhichYouDoNotHaveChat(person, id).observeForever {
            usersLiveData.postValue(it)
        }
    }

    fun getCurrentUser() = dataUserFirebaseRepository.commonUser

    fun addPersonalChat(id: String, pair: Pair<String, Person>) =
        chatsFirebaseRepository.addPersonalChat(id, pair)
}