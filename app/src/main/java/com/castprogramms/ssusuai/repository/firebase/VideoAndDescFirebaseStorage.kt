/*
package com.castprogramms.ssusuai.repository.firebase

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.castprogramms.ssusuai.repository.Resource
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage

class VideoAndDescFirebaseStorage : VideoAndDescApi {
    private val storage = FirebaseStorage.getInstance(BuildConfig.STORAGE_BUCKET)
    private val ref = storage.reference

    val settings = FirebaseFirestoreSettings.Builder()
        .setPersistenceEnabled(true)
        .build()
    val fireStore = FirebaseFirestore.getInstance(FirebaseApp.getInstance("test")).apply {
        firestoreSettings = settings
    }

    fun loadPhotoUser(uri: Uri, userID: String): MutableLiveData<Resource<String>> {
        val mutableLiveData = MutableLiveData<Resource<String>>(Resource.Loading())
        ref.child(imagesTag + userID).putFile(uri).addOnSuccessListener {
            ref.child(imagesTag + userID).downloadUrl.addOnCompleteListener {
                fireStore.collection(DataUserFirebase.studentTag)
                    .document(userID)
                    .update(EditProfile.IMG.desc, it.result.toString()).addOnCompleteListener {
                        if (it.isSuccessful)
                            mutableLiveData.postValue(Resource.Success("Всё успешно"))
                        else
                            mutableLiveData.postValue(Resource.Error(it.exception?.message))
                    }
            }
        }.addOnFailureListener{
            mutableLiveData.postValue(Resource.Error(it.message))
        }

        return mutableLiveData
    }

    companion object {
        const val imagesTag = "images/"
    }
}
*/
