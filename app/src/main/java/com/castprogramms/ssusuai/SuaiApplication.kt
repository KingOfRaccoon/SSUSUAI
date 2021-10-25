package com.castprogramms.ssusuai

import android.app.Application
import com.castprogramms.ssusuai.repository.firebase.DataUserFirebaseRepository
import com.castprogramms.ssusuai.repository.firebase.NewsFirebaseRepository
import com.castprogramms.ssusuai.ui.authentication.AuthenticationViewModel
import com.castprogramms.ssusuai.ui.profile.ProfileViewModel
import com.castprogramms.ssusuai.ui.registration.RegistrationViewModel
import com.castprogramms.ssusuai.ui.splash.SplashViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class SuaiApplication : Application() {
    private val modules = module {
        single {
            Firebase.firestore.apply {
                firestoreSettings = firestoreSettings {
                    isPersistenceEnabled = true
                }
            }
        }
        single { DataUserFirebaseRepository(get()) }
        single { NewsFirebaseRepository(get()) }
        viewModel { AuthenticationViewModel(get(), this@SuaiApplication) }
        viewModel { RegistrationViewModel(get()) }
        viewModel { SplashViewModel(get()) }
        viewModel { ProfileViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@SuaiApplication)
            modules(modules)
        }
    }
}