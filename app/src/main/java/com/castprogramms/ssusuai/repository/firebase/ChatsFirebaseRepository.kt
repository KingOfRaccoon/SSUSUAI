package com.castprogramms.ssusuai.repository.firebase

import androidx.lifecycle.MutableLiveData
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.repository.interfaces.ChatsInterface
import com.castprogramms.ssusuai.tools.chat.Chat
import com.castprogramms.ssusuai.tools.chat.PersonalChat
import com.castprogramms.ssusuai.tools.chat.PublicChat
import com.google.firebase.firestore.FirebaseFirestore

class ChatsFirebaseRepository(private val firebase: FirebaseFirestore): ChatsInterface(firebase) {

    companion object{
        const val chats_tag_personal = "chats_personal"
        const val chats_tag_public = "chats_public"
    }

    override fun getPersonalChats(ids: List<String>): MutableLiveData<Resource<List<Pair<String, PersonalChat>>>> {
        val mutableLiveData = MutableLiveData<Resource<List<Pair<String, PersonalChat>>>>(Resource.Loading())
        val collection = firebase.collection(chats_tag_personal)
        val listChats = mutableListOf<Pair<String, PersonalChat>>()
        ids.forEachIndexed { index, it ->
            collection.document(it).addSnapshotListener { value, _ ->
                val chat = value?.toObject(PersonalChat::class.java)
                if (chat != null)
                    listChats.add(it to chat)
            }

            if (index == ids.lastIndex)
                mutableLiveData.postValue(Resource.Success(listChats))
        }

        return mutableLiveData
    }

    override fun getPublicChats(ids: List<String>): MutableLiveData<Resource<List<Pair<String, PublicChat>>>> {
        val mutableLiveData = MutableLiveData<Resource<List<Pair<String, PublicChat>>>>(Resource.Loading())
        val collection = firebase.collection(chats_tag_public)
        val listChats = mutableListOf<Pair<String, PublicChat>>()
        ids.forEachIndexed { index, it ->
            collection.document(it).addSnapshotListener { value, _ ->
                val chat = value?.toObject(PublicChat::class.java)
                if (chat != null)
                    listChats.add(it to chat)
            }

            if (index == ids.lastIndex)
                mutableLiveData.postValue(Resource.Success(listChats))
        }

        return mutableLiveData
    }
}