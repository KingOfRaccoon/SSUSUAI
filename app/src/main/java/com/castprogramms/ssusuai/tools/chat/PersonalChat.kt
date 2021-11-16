package com.castprogramms.ssusuai.tools.chat

class PersonalChat(
    messages: List<Message> = listOf(),
    icon: String = "",
    val idFirstUser: String = "",
    val idSecondUser: String = ""
) : Chat(messages, icon, TypeChats.PersonalChat) {
    override fun equals(other: Any?): Boolean {
        return if (other is PersonalChat)
            this.idFirstUser == other.idFirstUser && this.idSecondUser == other.idSecondUser
                    && this.messages == other.messages && this.icon == other.icon
        else
            false
    }
}