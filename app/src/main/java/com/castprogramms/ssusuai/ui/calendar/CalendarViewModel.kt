package com.castprogramms.ssusuai.ui.calendar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.repository.firebase.DataUserFirebaseRepository
import com.castprogramms.ssusuai.repository.firebase.EventFirebaseRepository
import com.castprogramms.ssusuai.tools.CurrentDayEvents

class CalendarViewModel(
    private val dataUserFirebaseRepository: DataUserFirebaseRepository,
    private val eventFirebaseRepository: EventFirebaseRepository
) : ViewModel() {
    val liveDataEvents = MutableLiveData<Resource<CurrentDayEvents>>()

    fun getUser(id: String) = dataUserFirebaseRepository.getPerson(id)

    fun loadEvents(date: String){
        eventFirebaseRepository.getEventsOnThisDate(date).observeForever {
            if (it != liveDataEvents.value)
                liveDataEvents.postValue(it)
        }
    }
}
