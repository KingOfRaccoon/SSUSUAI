package com.castprogramms.ssusuai

import com.castprogramms.ssusuai.tools.time.DataTime
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testTimeModule(){
        val startTime = DataTime(2021, 0, 1, "02:31", 7)
        println(startTime.getServiceTime())
        assertEquals(startTime.getServiceTime(), "31 Декабря, 22:31")
    }
}