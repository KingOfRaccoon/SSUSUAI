package com.castprogramms.ssusuai.tools.chat

class Message(// message body
    val text: String, // data of the user that sent this message
    var memberData: MemberData,
    var belongsToCurrentUser: Boolean// is this message sent by us?
)