package com.castprogramms.ssusuai.tools.chat

import com.castprogramms.ssusuai.tools.time.DataTime
import java.util.*

open class Message(
    var idUser: String = "",// is this message sent by us?
    var id: String = UUID.randomUUID().toString(),
    val userWhoSee: List<String> = listOf(idUser),
    val date: DataTime = DataTime.now(),
    val idAnswerMessage: String = ""
)