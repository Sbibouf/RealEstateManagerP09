package com.example.realestatemanager

import com.example.realestatemanager.data.local.service.Utils
import com.example.realestatemanager.model.LoanData
import org.junit.Assert.*
import org.junit.Test
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.pow

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
    fun conversionToEuroWorks() {
        val euros: Int = Utils.convertDollarToEuro(100)
        assertEquals(81, euros.toLong())
    }

    @Test
    fun conversionToEuroWorks2() {
        val euros: Int = Utils.convertDollarToEuro(125)
        assertEquals(102, euros.toLong())
    }

    @Test
    fun conversionToDollarWorks() {
        val dollar: Int = Utils.convertEuroToDollar(100)
        assertEquals(123, dollar.toLong())
    }

    @Test
    fun conversionToDollarWorks2() {
        val dollar: Int = Utils.convertEuroToDollar(125)
        assertEquals(154, dollar.toLong())
    }

    @Test
    fun testGetTodayDate() {
        val currentDate = Date()
        val formattedDate = Utils.getTodayDate(currentDate)
        val expectedDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
        val expectedFormattedDate = expectedDateFormat.format(currentDate)

        assertEquals(expectedFormattedDate, formattedDate)
    }

    @Test
    fun testLoanCalculation(){
        val loanData = LoanData(150000.0, 15000.0, 2.3,25)

        assertEquals("592,13", calculateLoanDetail(loanData, "monthly"))
        assertEquals("42637,53", calculateLoanDetail(loanData, "interest"))
        assertEquals("177637,53", calculateLoanDetail(loanData, "total"))
    }

    private fun calculateLoanDetail(loanData: LoanData, type: String): String {
        val decimalFormat = DecimalFormat("#.##")
        val principal = loanData.amount - loanData.downPayment
        val monthlyInterestRate = loanData.yearlyInterestRate / 100.0 / 12.0
        val totalPaymentsDuration = loanData.loanTermYears * 12
        val interest =
            principal * (monthlyInterestRate) / (1 - (1 + monthlyInterestRate).pow(-totalPaymentsDuration.toDouble()))


        when (type) {
            "monthly" -> return decimalFormat.format(interest)
            "interest" -> return decimalFormat.format((interest * totalPaymentsDuration) - principal)
            "total" -> return decimalFormat.format(interest * totalPaymentsDuration)
        }
        return "0.0"
    }
}