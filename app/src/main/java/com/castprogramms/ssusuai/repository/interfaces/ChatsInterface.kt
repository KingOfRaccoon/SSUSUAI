package com.castprogramms.ssusuai.repository.interfaces

import androidx.lifecycle.MutableLiveData
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.tools.chat.Chat
import com.castprogramms.ssusuai.tools.chat.PersonalChat
import com.castprogramms.ssusuai.tools.chat.PublicChat
import com.google.firebase.firestore.FirebaseFirestore

abstract class ChatsInterface(private val firebase: FirebaseFirestore) {
    abstract fun getPersonalChats(ids: List<String>): MutableLiveData<Resource<List<Pair<String, PersonalChat>>>>

    abstract fun getPublicChats(ids: List<String>): MutableLiveData<Resource<List<Pair<String, PublicChat>>>>
}