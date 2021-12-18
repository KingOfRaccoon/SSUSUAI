package com.castprogramms.ssusuai.repository.firebase

import androidx.lifecycle.MutableLiveData
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.repository.interfaces.NewsInterface
import com.castprogramms.ssusuai.tools.New
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class NewsFirebaseRepository(private val firebase: FirebaseFirestore) : NewsInterface {

    companion object {
        const val newsTag = "news"
    }

    override fun getAllNews(): MutableLiveData<Resource<List<New>>> {
        val mutableLiveData = MutableLiveData<Resource<List<New>>>(Resource.Loading())
        firebase.collection(newsTag).addSnapshotListener { value, error ->
            if (value != null)
                mutableLiveData.postValue(Resource.Success(value.toObjects(New::class.java)))
            else
                mutableLiveData.postValue(Resource.Error(error?.message.toString()))
        }

        return mutableLiveData
    }

    override fun getNew(title: String): MutableLiveData<Resource<New>> {
        val mutableLiveData = MutableLiveData<Resource<New>>(Resource.Loading())
        firebase.collection(newsTag).whereEqualTo("title", title)
            .addSnapshotListener { value, error ->
                if (value != null && value.documents.first() != null) {
                    mutableLiveData.postValue(
                        Resource.Success(
                            value.documents.first().toObject(New::class.java)!!
                        )
                    )
                } else {
                    mutableLiveData.postValue(Resource.Error(error?.message.toString()))
                }
            }

        return mutableLiveData
    }

    override fun addNew(new: New): MutableLiveData<Resource<String>> {
        val mutableLiveData = MutableLiveData<Resource<String>>(Resource.Loading())
        firebase.collection(newsTag)
            .document(new.title)
            .set(new)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    mutableLiveData.postValue(Resource.Success("Успех"))
                } else {
                    mutableLiveData.postValue(Resource.Error(it.exception?.message.toString()))
                }
            }

        return mutableLiveData
    }

    override fun addLikeToNew(new: New, idUser: String) {
        firebase.collection(newsTag)
            .document(new.title)
            .update("likes", FieldValue.arrayUnion(idUser))
    }

    override fun addSeeToNew(new: New, idUser: String) {
        firebase.collection(newsTag)
            .document(new.title)
    }
}