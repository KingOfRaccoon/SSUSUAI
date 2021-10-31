package com.castprogramms.ssusuai.tools.chat

class MemberData(name: String?, color: String?) {
    var name: String? = name
        private set
    var color: String? = color
        private set

    override fun toString(): String {
        return "MemberData{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}'
    }
}