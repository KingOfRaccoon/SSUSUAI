package com.castprogramms.ssusuai

import android.app.Application
import android.app.usage.UsageEvents.Event.NONE
import android.bluetooth.BluetoothAdapter.ERROR
import com.castprogramms.ssusuai.repository.firebase.ChatsFirebaseRepository
import com.castprogramms.ssusuai.repository.firebase.DataUserFirebaseRepository
import com.castprogramms.ssusuai.repository.firebase.NewsFirebaseRepository
import com.castprogramms.ssusuai.ui.addAlbum.AddAlbumViewModel
import com.castprogramms.ssusuai.ui.addchat.AddPersonalChatViewModel
import com.castprogramms.ssusuai.ui.authentication.AuthenticationViewModel
import com.castprogramms.ssusuai.ui.calendar.CalendarViewModel
import com.castprogramms.ssusuai.ui.chat.ChatViewModel
import com.castprogramms.ssusuai.ui.chats.ChatsViewModel
import com.castprogramms.ssusuai.ui.gallery.GalleryViewModel
import com.castprogramms.ssusuai.ui.news.NewsViewModel
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
import java.util.logging.Level

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
        single { ChatsFirebaseRepository(get()) }
        viewModel { MainActivityViewModel(get()) }
        viewModel { AuthenticationViewModel(get(), this@SuaiApplication) }
        viewModel { RegistrationViewModel(get()) }
        viewModel { SplashViewModel(get()) }
        viewModel { ProfileViewModel(get()) }
        viewModel { CalendarViewModel(get())}
        viewModel { NewsViewModel(get())}
        viewModel { GalleryViewModel(get())}
        viewModel { AddAlbumViewModel()}
        viewModel { ChatsViewModel(get(), get(), this@SuaiApplication) }
        viewModel { AddPersonalChatViewModel(get(), get()) }
        single { ChatViewModel(get(), get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) org.koin.core.logger.Level.ERROR else org.koin.core.logger.Level.NONE)
            androidContext(this@SuaiApplication)
            modules(modules)
        }
    }
}