package com.castprogramms.ssusuai.tools

import com.castprogramms.ssusuai.tools.time.DataTime

data class New(
    val title: String = "",
    val body: String = "",
    val titleImg: String = "",
    val date: DataTime = DataTime(),
    val imgs: List<String> = listOf()
)