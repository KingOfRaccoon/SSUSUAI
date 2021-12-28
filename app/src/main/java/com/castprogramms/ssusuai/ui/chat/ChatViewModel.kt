package com.castprogramms.ssusuai.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.repository.firebase.ChatsFirebaseRepository
import com.castprogramms.ssusuai.repository.firebase.DataUserFirebaseRepository
import com.castprogramms.ssusuai.tools.chat.Chat
import com.castprogramms.ssusuai.tools.chat.Message
import com.castprogramms.ssusuai.tools.chat.TypeChats
import com.castprogramms.ssusuai.users.Person

class ChatViewModel(
    private val chatsFirebaseRepository: ChatsFirebaseRepository,
    private val dataUserFirebaseRepository: DataUserFirebaseRepository
) : ViewModel() {
    val mutableLiveDataChat = MutableLiveData<Resource<out Chat>>()
    val mutableLiveDataOtherUser = MutableLiveData<Resource<Person>>()
    val messages = mutableMapOf<String, String>()
    private val observer = Observer<Resource<Person>> {
        mutableLiveDataOtherUser.postValue(it)
    }

    fun <T : Chat> getChat(idChat: String, typeChats: TypeChats) {
        chatsFirebaseRepository.getChat<T>(idChat, typeChats).observeForever {
            mutableLiveDataChat.postValue(it as Resource<T>)
        }
    }

    fun addMessage(idChat: String, message: Message) =
        chatsFirebaseRepository.addMessage(idChat, message)

    fun getUser(id: String) = dataUserFirebaseRepository.getPerson(id)

    fun getPersonWithoutLiveData(id: String) {
        dataUserFirebaseRepository.getPersonWithoutLiveData(id).observeForever(observer)
    }
}