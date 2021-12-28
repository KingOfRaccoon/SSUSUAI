package com.castprogramms.ssusuai.repository.firebase

import androidx.lifecycle.MutableLiveData
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.repository.interfaces.EventInterface
import com.castprogramms.ssusuai.tools.CurrentDayEvents
import com.castprogramms.ssusuai.tools.Event
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class EventFirebaseRepository(private val firebaseFirestore: FirebaseFirestore): EventInterface {

    companion object{
        const val events_tag = "events"
    }

    override fun addEvent(event: Event): MutableLiveData<Resource<String>> {
        val mutableLiveData = MutableLiveData<Resource<String>>(Resource.Loading())
        firebaseFirestore.collection(events_tag)
            .document(event.date.toString())
            .update("events", FieldValue.arrayUnion(event))
            .addOnCompleteListener {
                if (it.isSuccessful){
                    mutableLiveData.postValue(Resource.Success("Успех"))
                } else {
                    mutableLiveData.postValue(Resource.Error(it.exception?.message.toString()))
                }
            }

        return mutableLiveData
    }

    override fun getEventsOnThisDate(date: String): MutableLiveData<Resource<CurrentDayEvents>> {
        val mutableLiveData = MutableLiveData<Resource<CurrentDayEvents>>(Resource.Loading())
        firebaseFirestore.collection(events_tag)
            .document(date)
            .addSnapshotListener { value, error ->
                if (value?.toObject(CurrentDayEvents::class.java) != null)
                    mutableLiveData.postValue(Resource.Success(
                        value.toObject(CurrentDayEvents::class.java)!!
                    ))
                else
                    mutableLiveData.postValue(Resource.Error(
                        error?.message.toString()
                    ))
            }

        return mutableLiveData
    }
}