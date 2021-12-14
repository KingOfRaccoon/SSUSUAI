package com.castprogramms.ssusuai.tools.chat

import com.castprogramms.ssusuai.tools.time.DataTime
import java.util.*

class Message(// message body
    val text: String = "", // data of the user that sent this message
    val idUser: String = "",// is this message sent by us?
    var id: String = UUID.randomUUID().toString(),
    val userWhoSee: List<String> = listOf(idUser),
    val date: DataTime = DataTime.now()
)