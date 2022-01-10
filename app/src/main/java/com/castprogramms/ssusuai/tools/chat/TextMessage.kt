package com.castprogramms.ssusuai.tools.chat

import android.view.View
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemMessagePrivateBinding
import com.castprogramms.ssusuai.tools.time.DataTime
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import java.util.*

class TextMessage(// message body
    val text: String = "", // data of the user that sent this message
    idUser: String = ""
) : Message(idUser)