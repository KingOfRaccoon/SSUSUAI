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
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

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
        val firstQuery = firebase.collection(ChatsFirebaseRepository.chats_tag)
            .whereEqualTo("idFirstUser", id).get()
        val secondQuery = firebase.collection(ChatsFirebaseRepository.chats_tag)
            .whereEqualTo("idSecondUser", id).get()
        Tasks.whenAllSuccess<QuerySnapshot>(firstQuery, secondQuery).addOnCompleteListener {
            if (it.isSuccessful) {
                val list = mutableListOf<String>()
                it.result.forEach {
                    it.documents.forEach {
                        val firstId = it.getString("idFirstUser").toString()
                        val secondId = it.getString("idSecondUser").toString()
                        list.add(if (firstId == id) secondId else firstId)
                    }
                }

                firebase.collection(users_tag).whereNotIn(FieldPath.documentId(), list + listOf(id)).addSnapshotListener { value, error ->
                    if (value != null){
                        val persons = mutableListOf<Pair<String, Person>>()
                        value.documents.forEach {
                            if (it.toObject(Person::class.java) != null)
                                persons.add(
                                    it.id to it.toObject(Person::class.java)!!
                                )
                        }
                        mutableLiveData.postValue(Resource.Success(persons))
                    } else {
                        mutableLiveData.postValue(Resource.Error(error?.message.toString()))
                    }
                }
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