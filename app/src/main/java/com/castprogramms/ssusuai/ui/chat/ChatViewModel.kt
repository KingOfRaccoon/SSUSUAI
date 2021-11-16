package com.castprogramms.ssusuai.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.repository.firebase.ChatsFirebaseRepository
import com.castprogramms.ssusuai.tools.chat.Chat
import com.castprogramms.ssusuai.tools.chat.Message
import com.castprogramms.ssusuai.tools.chat.TypeChats

class ChatViewModel(private val chatsFirebaseRepository: ChatsFirebaseRepository) : ViewModel() {
    val mutableLiveDataChat = MutableLiveData<Resource<out Chat>>()

    fun <T : Chat> getChat(idChat: String, typeChats: TypeChats) {
        chatsFirebaseRepository.getChat<T>(idChat, typeChats).observeForever {
            mutableLiveDataChat.postValue(it as Resource<T>)
        }
    }

    fun addMessage(idChat: String, message: Message) = chatsFirebaseRepository.addMessage(idChat, message)
}