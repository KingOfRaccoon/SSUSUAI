package com.castprogramms.ssusuai.ui.addchat

import com.castprogramms.ssusuai.users.Person


interface AddPersonalChatCallback {
    fun addPersonalChat(pair: Pair<String, Person>)
}