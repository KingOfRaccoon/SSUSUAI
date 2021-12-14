package com.castprogramms.ssusuai.tools.time

import com.google.firebase.firestore.IgnoreExtraProperties
import java.time.DayOfWeek
import java.util.*

data class DataTime(
    var year: Int = 0,
    var mouth: Int = 0,
    var day: Int = 0,
    var time: String = "",
    var dayOfWeek: Int = 0,
    var timeZone: Int = 0
) {
    constructor(calendar: Calendar) : this(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH),
        calendar.get(Calendar.HOUR_OF_DAY)
            .toString() + ":" + if (calendar.get(Calendar.MINUTE) >= 10) calendar.get(Calendar.MINUTE)
            .toString() else "0${calendar.get(Calendar.MINUTE)}",
        calendar.get(Calendar.DAY_OF_WEEK),
        (calendar.timeZone.rawOffset / 1000 / 3600)
    )

    fun getServiceTime(): String {
        val diffOffsetTime =
            (Calendar.getInstance().timeZone.rawOffset / 1000 / 3600) - timeZone
        val time = time.split(":")
        val calendar = Calendar.getInstance()
        calendar.set(
            year,
            mouth,
            day,
            time[0].toInt(),
            time[1].toInt()
        )
        calendar.timeZone.rawOffset = timeZone * 3600 * 1000
        calendar.add(Calendar.HOUR, diffOffsetTime)
        val newDataTime = DataTime(calendar)

        var string = ""
        val date = now()
        when (date.day - newDataTime.day) {
            -1 -> string += "Завтра"
            0 -> string += "Сегодня"
            1 -> string += "Вчера"
            else -> {
                string += newDataTime.day.toString()
                string += " "
                string += getMouth(newDataTime.mouth)
            }
        }
        string += ", "
        string += newDataTime.time
        return string
    }

    fun getTimeForChat(): String{
        val diffOffsetTime =
            (Calendar.getInstance().timeZone.rawOffset / 1000 / 3600) - timeZone
        val time = time.split(":")
        val calendar = Calendar.getInstance()
        calendar.set(
            year,
            mouth,
            day,
            time[0].toInt(),
            time[1].toInt()
        )
        calendar.timeZone.rawOffset = timeZone * 3600 * 1000
        calendar.add(Calendar.HOUR, diffOffsetTime)
        val newDataTime = DataTime(calendar)

        var string = ""
        val date = now()
        when (date.day - newDataTime.day) {
//            -1 -> string += "Завтра"
            0 -> string += "Сегодня, "
            1 -> string += "Вчера, "
            else -> {
//                string += newDataTime.day.toString()
//                string += " "
//                string += getMouth(newDataTime.mouth)
            }
        }
        string += newDataTime.day.toString()
        string += " "
        string += getMouth(newDataTime.mouth)
        return string
    }



    fun getShortcutDayOfWeek() = when(dayOfWeek){
        Calendar.MONDAY -> "Пн"
        Calendar.TUESDAY -> "Вт"
        Calendar.WEDNESDAY -> "Ср"
        Calendar.THURSDAY -> "Чт"
        Calendar.FRIDAY -> "Пт"
        Calendar.SATURDAY -> "Сб"
        Calendar.SUNDAY -> "Вс"
        else -> ""
    }

    fun getMouthAndYear() = when (mouth) {
        0 -> "Января"
        1 -> "Февраля"
        2 -> "Марта"
        3 -> "Апреля"
        4 -> "Мая"
        5 -> "Июня"
        6 -> "Июля"
        7 -> "Августа"
        8 -> "Сентября"
        9 -> "Октября"
        10 -> "Ноября"
        11 -> "Декабря"
        else -> ""
    } + " $year"

    fun getDayOfWeekText() = when(dayOfWeek){
        Calendar.MONDAY -> "Понедельник"
        Calendar.TUESDAY -> "Вторник"
        Calendar.WEDNESDAY -> "Среда"
        Calendar.THURSDAY -> "Четверг"
        Calendar.FRIDAY -> "Пятница"
        Calendar.SATURDAY -> "Суббота"
        Calendar.SUNDAY -> "Воскресенье"
        else -> ""
    }


    companion object {
        fun now() = DataTime(Calendar.getInstance())

        private fun getMouth(mouth: Int) = when (mouth) {
            0 -> "Января"
            1 -> "Февраля"
            2 -> "Марта"
            3 -> "Апреля"
            4 -> "Мая"
            5 -> "Июня"
            6 -> "Июля"
            7 -> "Августа"
            8 -> "Сентября"
            9 -> "Октября"
            10 -> "Ноября"
            11 -> "Декабря"
            else -> ""
        }
    }
}