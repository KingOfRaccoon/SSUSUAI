package com.castprogramms.ssusuai.repository.firebase

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.castprogramms.ssusuai.repository.interfaces.DataUserInterface
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.tools.EditProfile
import com.castprogramms.ssusuai.tools.chat.PersonalChat
import com.castprogramms.ssusuai.users.Admin
import com.castprogramms.ssusuai.users.CommonUser
import com.castprogramms.ssusuai.users.Person
import com.castprogramms.ssusuai.users.TypeOfPerson
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.firestore.FirebaseFirestore

class DataUserFirebaseRepository(
    private val firebase: FirebaseFirestore,
    private val videoAndDescFirebaseStorage: VideoAndDescFirebaseStorage,
    val context: Context
) : DataUserInterface {

    companion object {
        const val users_tag = "users"
    }

    val person = MutableLiveData<Resource<Person>>()
    var typeOfPerson = TypeOfPerson.User
    var googleAccount: GoogleSignInAccount? = null

    override fun getPerson(id: String): MutableLiveData<Resource<Person>> {
        val mutableLiveData = MutableLiveData<Resource<Person>>(Resource.Loading())
        firebase.collection(users_tag)
            .document(id)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    when (value.getString("typeOfPerson")) {
                        TypeOfPerson.Admin.name -> {
                            mutableLiveData.postValue(Resource.Success(value.toObject(Admin::class.java)!!))
                            person.postValue(Resource.Success(value.toObject(Admin::class.java)!!))
                            typeOfPerson = TypeOfPerson.Admin
                        }
                        TypeOfPerson.User.name -> {
                            mutableLiveData.postValue(Resource.Success(value.toObject(CommonUser::class.java)!!))
                            person.postValue(Resource.Success(value.toObject(CommonUser::class.java)!!))
                            typeOfPerson = TypeOfPerson.User
                        }
                        else ->
                            mutableLiveData.postValue(Resource.Error(error?.message.toString()))
                    }
                } else {
                    mutableLiveData.postValue(Resource.Error(error?.message.toString()))
                }
            }

        return mutableLiveData
    }

    override fun addPerson(id: String, person: Person): MutableLiveData<Resource<String>> {
        val mutableLiveData = MutableLiveData<Resource<String>>(Resource.Loading())
        firebase.collection(users_tag).document(id).set(person).addOnSuccessListener { _: Void? ->
            mutableLiveData.postValue(Resource.Success("Success"))
        }.addOnFailureListener {
            mutableLiveData.postValue(Resource.Error(it.message.toString()))
        }

        return mutableLiveData
    }


    override fun getPersonWhichYouDoNotHaveChat(
        person: Person,
        id: String
    ): MutableLiveData<Resource<List<Pair<String, Person>>>> {
        val mutableLiveData =
            MutableLiveData<Resource<List<Pair<String, Person>>>>(Resource.Loading())
        firebase.collection(users_tag).addSnapshotListener { value, error ->
            if (value != null) {
                val list = mutableListOf<Pair<String, Person>>()
                value.documents.forEach {
                    checkPersonNotInChats(person, it.id).observeForever { bool ->
                        if (!bool && id != it.id) {
                            val user = it.toObject(Person::class.java)
                            if (user != null)
                                list.add(it.id to user)
                        }
                    }
                }
                mutableLiveData.postValue(Resource.Success(list))
            } else {
                mutableLiveData.postValue(Resource.Error(error?.message.toString()))
            }
        }

        return mutableLiveData
    }
//TODO починить обновление данных

    fun editNameStudent(first_name: String, userID: String) {
        firebase.collection(users_tag)
            .document(userID)
            .update(EditProfile.FIRST_NAME.desc, first_name)
    }

    fun editLastNameStudent(second_name: String, userID: String) {
        firebase.collection(users_tag)
            .document(userID)
            .update(EditProfile.SECOND_NAME.desc, second_name)
    }

    private fun checkPersonNotInChats(person: Person, id: String): MutableLiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        if (person.idsPersonalChat.isEmpty()) {
            mutableLiveData.postValue(false)
            return mutableLiveData
        }
        person.idsPersonalChat.forEach {
            firebase.collection(ChatsFirebaseRepository.chats_tag)
                .document(it).get().addOnSuccessListener {
                    val chat = it.toObject(PersonalChat::class.java)
                    if (chat != null)
                        mutableLiveData.postValue(chat.idFirstUser == id || chat.idSecondUser == id)
                }
        }
        return mutableLiveData
    }

    fun getPersonWithoutLiveData(id: String): MutableLiveData<Resource<Person>> {
        val mutableLiveData = MutableLiveData<Resource<Person>>(Resource.Loading())
        firebase.collection(users_tag)
            .document(id)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    when (value.getString("typeOfPerson")) {
                        TypeOfPerson.Admin.name -> {
                            mutableLiveData.postValue(Resource.Success(value.toObject(Admin::class.java)!!))
                        }
                        TypeOfPerson.User.name -> {
                            mutableLiveData.postValue(Resource.Success(value.toObject(CommonUser::class.java)!!))
                        }
                        else ->
                            mutableLiveData.postValue(Resource.Error(error?.message.toString()))
                    }
                } else {
                    mutableLiveData.postValue(Resource.Error(error?.message.toString()))
                }
            }
        return mutableLiveData
    }

    fun loadPhotoUser(uri: Uri, userId: String) =
        videoAndDescFirebaseStorage.loadPhotoUser(uri, userId)

    fun loadPhotoUserInRegistration(uri: Uri, userId: String) =
        videoAndDescFirebaseStorage.loadPhotoUserInRegistration(uri, userId)

    fun getGoogleSignAccount(): GoogleSignInAccount? {
        if (googleAccount == null)
            googleAccount = GoogleSignIn.getLastSignedInAccount(context)
        return googleAccount
    }
}