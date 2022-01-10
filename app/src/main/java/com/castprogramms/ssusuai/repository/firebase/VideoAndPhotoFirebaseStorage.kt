package com.castprogramms.ssusuai.repository.firebase

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.repository.interfaces.VideoAndDescApi
import com.castprogramms.ssusuai.tools.EditProfile
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage

class VideoAndPhotoFirebaseStorage(storage: FirebaseStorage, val firebase: FirebaseFirestore) :
    VideoAndDescApi {
    private val ref = storage.reference

    fun loadPhotoUser(uri: Uri, userID: String): MutableLiveData<Resource<String>> {
        val mutableLiveData = MutableLiveData<Resource<String>>(Resource.Loading())
        ref.child(imagesTag + userID).putFile(uri).addOnSuccessListener {
            ref.child(imagesTag + userID).downloadUrl.addOnCompleteListener {
                firebase.collection(DataUserFirebaseRepository.users_tag)
                    .document(userID)
                    .update(EditProfile.IMG.desc, it.result.toString()).addOnCompleteListener {
                        if (it.isSuccessful) {
                            mutableLiveData.postValue(Resource.Success("Всё успешно"))
                        } else {
                            mutableLiveData.postValue(Resource.Error(it.exception?.message.toString()))
                        }
                    }
            }
        }.addOnFailureListener {
            mutableLiveData.postValue(Resource.Error(it.message.toString()))
        }

        return mutableLiveData
    }

    fun loadPhotoUserInRegistration(uri: Uri, userID: String): MutableLiveData<Resource<Uri>> {
        val mutableLiveData = MutableLiveData<Resource<Uri>>(Resource.Loading())
        ref.child(imagesTag + userID).putFile(uri).addOnSuccessListener {
            ref.child(imagesTag + userID).downloadUrl.addOnCompleteListener {
                if (it.isSuccessful){
                    mutableLiveData.postValue(Resource.Success(it.result))
                } else {
                    mutableLiveData.postValue(Resource.Error(it.exception?.message.toString()))
                }
            }
        }.addOnFailureListener {
            mutableLiveData.postValue(Resource.Error(it.message.toString()))
        }

        return mutableLiveData
    }

    fun loadPhotoInNews(uri: Uri): MutableLiveData<Resource<Uri>>{
        val mutableLiveData = MutableLiveData<Resource<Uri>>(Resource.Loading())
        ref.child(imagesNewsTag + uri.path).putFile(uri).addOnSuccessListener {
            ref.child(imagesNewsTag + uri.path).downloadUrl.addOnCompleteListener {
                if (it.isSuccessful){
                    mutableLiveData.postValue(Resource.Success(it.result))
                } else {
                    mutableLiveData.postValue(Resource.Error(it.exception?.message.toString()))
                }
            }
        }.addOnFailureListener {
            mutableLiveData.postValue(Resource.Error(it.message.toString()))
        }


        return mutableLiveData
    }

    companion object {
        const val imagesNewsTag = "images_news/"
        const val imagesTag = "images/"
    }
}
