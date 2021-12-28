package com.castprogramms.ssusuai.repository.interfaces

import androidx.lifecycle.MutableLiveData
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.tools.CurrentDayEvents
import com.castprogramms.ssusuai.tools.Event

interface EventInterface {
    fun addEvent(event: Event): MutableLiveData<Resource<String>>

    fun getEventsOnThisDate(date: String): MutableLiveData<Resource<CurrentDayEvents>>
}