package com.castprogramms.ssusuai.tools

import com.castprogramms.ssusuai.tools.time.DataTime
import java.io.Serializable

data class New(
    val title: String = "",
    val body: String = "",
    val titleImg: String = "",
    val imgs: List<String> = listOf(),
    val date: DataTime = DataTime.now(),
    val likes: List<String> = listOf(),
    val sees: List<String> = listOf()
): Serializable