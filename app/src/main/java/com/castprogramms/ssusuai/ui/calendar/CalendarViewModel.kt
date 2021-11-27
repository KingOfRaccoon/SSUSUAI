package com.castprogramms.ssusuai.ui.calendar

import androidx.lifecycle.ViewModel
import com.castprogramms.ssusuai.repository.firebase.DataUserFirebaseRepository

class CalendarViewModel(
    private val dataUserFirebaseRepository: DataUserFirebaseRepository
) : ViewModel() {
    fun getUser(id: String) = dataUserFirebaseRepository.getPerson(id)
}
