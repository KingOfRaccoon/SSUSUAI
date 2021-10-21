package com.castprogramms.ssusuai.repository.interfaces

import androidx.lifecycle.MutableLiveData
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.users.Person

interface DataUserInterface {
    fun getPerson(id: String): MutableLiveData<Resource<Person>>
    fun addPerson(id: String, person: Person): MutableLiveData<Resource<String>>
}