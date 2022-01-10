package com.castprogramms.ssusuai.ui.addNew

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.repository.firebase.NewsFirebaseRepository
import com.castprogramms.ssusuai.repository.firebase.VideoAndPhotoFirebaseStorage
import com.castprogramms.ssusuai.tools.New

class AddNewViewModel(
    private val newsFirebaseRepository: NewsFirebaseRepository,
    private val videoAndPhotoFirebaseStorage: VideoAndPhotoFirebaseStorage
): ViewModel() {
    val newImageLiveData = MutableLiveData<Resource<Uri>>()

    fun addNew(new: New) = newsFirebaseRepository.addNew(new)

    fun loadPhoto(uri: Uri){
        videoAndPhotoFirebaseStorage.loadPhotoInNews(uri).observeForever {
            newImageLiveData.postValue(it)
        }
    }
}