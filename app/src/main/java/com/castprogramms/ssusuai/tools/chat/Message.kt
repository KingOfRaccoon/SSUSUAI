package com.castprogramms.ssusuai.tools.chat

import java.util.*

class Message(// message body
    val text: String = "", // data of the user that sent this message
    var idUser: String = "",// is this message sent by us?
    var id: String = UUID.randomUUID().toString()
)