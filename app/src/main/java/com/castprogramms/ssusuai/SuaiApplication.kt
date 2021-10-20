package com.castprogramms.ssusuai

import android.app.Application
import com.castprogramms.ssusuai.repository.firebase.DataUserFirebaseRepository
import com.castprogramms.ssusuai.ui.authentication.AuthenticationViewModel
import com.castprogramms.ssusuai.ui.registration.RegistrationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class SuaiApplication: Application() {
    private val modules = module{
        single { DataUserFirebaseRepository() }
        viewModel { AuthenticationViewModel(get(), this@SuaiApplication) }
        viewModel { RegistrationViewModel(get()) }
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