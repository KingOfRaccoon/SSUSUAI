package com.castprogramms.ssusuai.tools.chat

class PublicChat(
    messages: List<Message> = listOf(),
    icon: String = "",
    val idsUsers: List<String> = listOf(),
    val name: String = ""
) : Chat(messages, icon) {
    override fun equals(other: Any?): Boolean {
        return if (other is PublicChat)
            this.idsUsers == other.idsUsers && this.name == other.name
                    && this.messages == other.messages && this.icon == other.icon
        else
            false
    }
}