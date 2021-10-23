package com.castprogramms.ssusuai.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.castprogramms.ssusuai.SuaiApplication
import com.castprogramms.ssusuai.repository.firebase.DataUserFirebaseRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn

class ProfileViewModel(
    private val dataUserFirebaseRepository: DataUserFirebaseRepository,
    application: Application
) : AndroidViewModel(application) {
    fun getUser() {
        val googleAccount = GoogleSignIn
            .getLastSignedInAccount((getApplication() as SuaiApplication).applicationContext)
//        if ()
    }
}