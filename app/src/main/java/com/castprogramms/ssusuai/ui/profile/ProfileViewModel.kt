package com.castprogramms.ssusuai.ui.profile

import androidx.lifecycle.ViewModel
import com.castprogramms.ssusuai.repository.firebase.DataUserFirebaseRepository

class ProfileViewModel(
    private val dataUserFirebaseRepository: DataUserFirebaseRepository
) : ViewModel() {
    fun getCommonUser() = dataUserFirebaseRepository.commonUser
}
