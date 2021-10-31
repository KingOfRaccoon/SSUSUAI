package com.castprogramms.ssusuai.tools.time

import java.util.*

data class DataTime(
    var year: Int = 0,
    var mouth: Int = 0,
    var day: Int = 0,
    var time: String = "",
    var timeZone: String = ""
){
    constructor(date: Date): this(date.year, date.month, date.date, "${date.hours}:${date.minutes}", date.timezoneOffset.toString())
}