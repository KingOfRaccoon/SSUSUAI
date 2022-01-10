package com.castprogramms.ssusuai.repository.firebase

import androidx.lifecycle.MutableLiveData
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.repository.interfaces.ChatsInterface
import com.castprogramms.ssusuai.tools.chat.*
import com.castprogramms.ssusuai.users.Person
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class ChatsFirebaseRepository(private val firebase: FirebaseFirestore) : ChatsInterface(firebase) {

    companion object {
        const val chats_tag = "chats"
//        const val chats_tag_public = "chats_public"
    }

    override fun getPersonalChats(ids: List<String>): MutableLiveData<Resource<List<Pair<String, PersonalChat>>>> {
        val mutableLiveData =
            MutableLiveData<Resource<List<Pair<String, PersonalChat>>>>(Resource.Loading())
        val collection =
            firebase.collection(chats_tag).whereEqualTo("typeChats", TypeChats.PersonalChat.name)
        val listChats = mutableListOf<Pair<String, PersonalChat>>()
        collection.addSnapshotListener { value, _ ->
            value?.documents?.forEach {
                if (it.id in ids) {
                    val chat = it.toObject(PersonalChat::class.java)
                    if (chat != null)
                        listChats.add(it.id to chat)
                }
                if (value.documents.last() == it)
                    mutableLiveData.postValue(Resource.Success(listChats))
            }
        }

        return mutableLiveData
    }

    override fun getPublicChats(ids: List<String>): MutableLiveData<Resource<List<Pair<String, PublicChat>>>> {
        val mutableLiveData =
            MutableLiveData<Resource<List<Pair<String, PublicChat>>>>(Resource.Loading())
        val collection =
            firebase.collection(chats_tag).whereEqualTo("typeChats", TypeChats.PublicChat)
        val listChats = mutableListOf<Pair<String, PublicChat>>()
        ids.forEachIndexed { index, it ->
            collection.addSnapshotListener { value, _ ->
                value?.documents?.forEach {
                    if (it.id in ids) {
                        val chat = it.toObject(PublicChat::class.java)
                        if (chat != null)
                            listChats.add(it.id to chat)
                    }
                }
            }

            if (index == ids.lastIndex)
                mutableLiveData.postValue(Resource.Success(listChats))
        }

        return mutableLiveData
    }

    override fun addPersonalChat(
        id: String,
        pair: Pair<String, Person>
    ): MutableLiveData<Resource<String>> {
        val mutableLiveData = MutableLiveData<Resource<String>>(Resource.Loading())
//        val documentFirstPerson = firebase.collection(DataUserFirebaseRepository.users_tag)
//            .document(id)
//        val documentSecondPerson = firebase.collection(DataUserFirebaseRepository.users_tag)
//            .document(pair.first)

        firebase.runTransaction {
            val idChat = UUID.randomUUID().toString()

            firebase.collection(chats_tag)
                .document(idChat)
                .set(PersonalChat(listOf(), "", id, pair.first))

            firebase.collection(DataUserFirebaseRepository.users_tag)
                .document(id)
                .update("idsPersonalChat", FieldValue.arrayUnion(idChat))

            firebase.collection(DataUserFirebaseRepository.users_tag)
                .document(pair.first)
                .update("idsPersonalChat", FieldValue.arrayUnion(idChat))

            idChat
        }.addOnSuccessListener {
            mutableLiveData.postValue(Resource.Success(it))
        }

        return mutableLiveData
    }

    override fun <T : Chat> getChat(
        id: String,
        typeChats: TypeChats
    ): MutableLiveData<Resource<T>> {
        val mutableLiveData = MutableLiveData<Resource<T>>()
        firebase.collection(chats_tag).document(id)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    when (typeChats) {
                        TypeChats.PersonalChat -> {
                            val chat = value.toObject(PersonalChat::class.java)
                            if (chat != null)
                                mutableLiveData.postValue(Resource.Success(chat as T))
                        }
                        TypeChats.PublicChat -> {
                            val chat = value.toObject(PublicChat::class.java)
                            if (chat != null)
                                mutableLiveData.postValue(Resource.Success(chat as T))
                        }
                    }
                } else {
                    mutableLiveData.postValue(Resource.Error(error?.message.toString()))
                }
            }
        return mutableLiveData
    }

    fun addMessage(idChat: String, textMessage: TextMessage) {
        firebase.collection(chats_tag).document(idChat)
            .update("messages", FieldValue.arrayUnion(textMessage))
    }

    fun getMessage(idChat: String, idMessage: String): MutableLiveData<Resource<String>> {
        val mutableLiveData = MutableLiveData<Resource<String>>()
        firebase.collection(chats_tag).document(idChat)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful)
                    mutableLiveData.postValue(
                        Resource.Success(
                            (it.result.get("messages") as List<TextMessage>)
                                .find { it.id == idMessage }?.text.toString()
                        )
                    )
                else
                    mutableLiveData.postValue(Resource.Error(it.exception?.message.toString()))
            }
        return mutableLiveData
    }
}