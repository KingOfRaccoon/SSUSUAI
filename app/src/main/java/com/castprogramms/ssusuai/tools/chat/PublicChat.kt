package com.castprogramms.ssusuai.tools.chat

class PublicChat(
    textMessages: List<TextMessage> = listOf(),
    icon: String = "",
    val idsUsers: List<String> = listOf(),
    val name: String = ""
) : Chat(textMessages, icon, TypeChats.PublicChat) {
    override fun equals(other: Any?): Boolean {
        return if (other is PublicChat)
            this.idsUsers == other.idsUsers && this.name == other.name
                    && this.messages == other.messages && this.icon == other.icon
        else
            false
    }
}