package com.castprogramms.ssusuai.repository.firebase

import androidx.lifecycle.MutableLiveData
import com.castprogramms.ssusuai.repository.interfaces.DataUserInterface
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.users.Admin
import com.castprogramms.ssusuai.users.CommonUser
import com.castprogramms.ssusuai.users.Person
import com.castprogramms.ssusuai.users.TypeOfPerson
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase

class DataUserFirebaseRepository : DataUserInterface {
    private val firebase = Firebase.firestore.apply {
        firestoreSettings = firestoreSettings {
            isPersistenceEnabled = true
        }
    }

    companion object {
        const val users_tag = "users"
    }

    override fun getPerson(id: String): MutableLiveData<Resource<Person>> {
        val mutableLiveData = MutableLiveData<Resource<Person>>(Resource.Loading())
        firebase.collection(users_tag)
            .document(id)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    when (value.getString("typeOfPerson")) {
                        TypeOfPerson.Admin.name ->
                            mutableLiveData.postValue(Resource.Success(value.toObject(Admin::class.java)!!))
                        TypeOfPerson.User.name ->
                            mutableLiveData.postValue(Resource.Success(value.toObject(CommonUser::class.java)!!))
                        else ->
                            mutableLiveData.postValue(Resource.Error(error?.message.toString()))
                    }
                } else {
                    mutableLiveData.postValue(Resource.Error(error?.message.toString()))
                }
            }

        return mutableLiveData
    }

    override fun addPerson(id: String, person: Person) {
        firebase.collection(users_tag).document(id).set(person)
    }

}