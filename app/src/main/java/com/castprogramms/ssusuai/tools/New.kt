package com.castprogramms.ssusuai.tools

import com.castprogramms.ssusuai.tools.time.DataTime
import java.io.Serializable

data class New(
    val title: String = "",
    val body: String = "",
    val titleImg: String = "",
    val date: DataTime = DataTime(),
    val imgs: List<String> = listOf()
): Serializable