package com.castprogramms.ssusuai.ui.authentication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.castprogramms.ssusuai.SuaiApplication
import com.castprogramms.ssusuai.repository.firebase.DataUserFirebaseRepository
import com.castprogramms.ssusuai.repository.interfaces.DataUserInterface
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class AuthenticationViewModel(
    private val dataUserInterface: DataUserFirebaseRepository,
    application: Application
) : AndroidViewModel(application) {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail().build()

    val googleSignInClient: GoogleSignInClient by lazy {
        GoogleSignIn.getClient(
            (this.getApplication() as SuaiApplication).applicationContext,
            gso
        )
    }

    fun getUser(userId: String) = dataUserInterface.getPerson(userId)
}