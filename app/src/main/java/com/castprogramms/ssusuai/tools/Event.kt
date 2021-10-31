package com.castprogramms.ssusuai.tools

import com.castprogramms.ssusuai.tools.time.DataTime

data class Event(
    var name: String = "",
    var date: DataTime = DataTime(),
    val img: String = "",
    val desc: String = ""
)