package com.castprogramms.ssusuai.ui.chats

import java.io.Serializable

enum class ChatsType(val text: String):Serializable {
    Group("Группы"),
    PERSONAL("Личные")
}