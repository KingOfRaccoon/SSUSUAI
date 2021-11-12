package com.castprogramms.ssusuai.ui.chats

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.castprogramms.ssusuai.SuaiApplication
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.repository.firebase.ChatsFirebaseRepository
import com.castprogramms.ssusuai.repository.firebase.DataUserFirebaseRepository
import com.castprogramms.ssusuai.repository.interfaces.ChatsInterface
import com.castprogramms.ssusuai.tools.NeedTools.lifecycleOwner
import com.castprogramms.ssusuai.tools.chat.Chat
import com.castprogramms.ssusuai.tools.chat.PersonalChat
import com.castprogramms.ssusuai.tools.chat.PublicChat
import com.castprogramms.ssusuai.users.Person
import com.castprogramms.ssusuai.users.TypeOfPerson

class ChatsViewModel(
    private val chatsInterface: ChatsFirebaseRepository,
    private val dataUserFirebaseRepository: DataUserFirebaseRepository,
    application: SuaiApplication
) :
    AndroidViewModel(application) {
    val liveDataPersonalChats = MutableLiveData<Resource<List<Pair<String, PersonalChat>>>>()
    val liveDataPublicChats = MutableLiveData<Resource<List<Pair<String, PublicChat>>>>()

    fun getPersonalChats(ids: List<String>) {
        chatsInterface.getPersonalChats(ids).observeForever {
                liveDataPersonalChats.postValue(it)
            }
    }

    fun getPublicChats(ids: List<String>) {
        chatsInterface.getPublicChats(ids).observeForever {
            liveDataPublicChats.postValue(it)
        }
    }

    fun <T: Person> getUser(): MutableLiveData<Resource<T>> {
        return when (dataUserFirebaseRepository.typeOfPerson){
            TypeOfPerson.Admin -> {
                dataUserFirebaseRepository.admin as MutableLiveData<Resource<T>>
            }
            TypeOfPerson.User -> {
                dataUserFirebaseRepository.commonUser as MutableLiveData<Resource<T>>
            }
        }
    }

    fun getTypeUser() = dataUserFirebaseRepository.typeOfPerson

    override fun onCleared() {
        super.onCleared()
    }
}