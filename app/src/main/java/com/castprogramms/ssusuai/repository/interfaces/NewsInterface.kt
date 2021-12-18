package com.castprogramms.ssusuai.repository.interfaces

import androidx.lifecycle.MutableLiveData
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.tools.New

interface NewsInterface {
    fun getAllNews(): MutableLiveData<Resource<List<New>>>
    fun getNew(title: String): MutableLiveData<Resource<New>>
    fun addNew(new: New): MutableLiveData<Resource<String>>
    fun addLikeToNew(new: New, idUser: String)
    fun addSeeToNew(new: New, idUser: String)
}