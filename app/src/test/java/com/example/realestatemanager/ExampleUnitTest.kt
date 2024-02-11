package com.example.realestatemanager

import com.example.realestatemanager.data.local.service.Utils
import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {
        assertEquals(4, (2 + 2).toLong())
    }


    @Test
    fun conversionTOEuroWorks() {
        val euros: Int = Utils.convertDollarToEuro(100)
        assertEquals(81, euros.toLong())
    }

    @Test
    fun conversionToDollarWorks() {
        val dollar: Int = Utils.convertEuroToDollar(100)
        assertEquals(123, dollar.toLong())
    }

    @Test
    fun testGetTodayDate() {
        val currentDate = Date()
        val formattedDate = Utils.getTodayDate(currentDate)
        val expectedDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
        val expectedFormattedDate = expectedDateFormat.format(currentDate)

        assertEquals(expectedFormattedDate, formattedDate)
    }
}