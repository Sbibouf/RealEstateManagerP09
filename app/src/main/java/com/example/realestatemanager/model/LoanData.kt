package com.example.realestatemanager.model

import kotlin.math.pow

data class LoanData(val amount: Double,
                    val downPayment: Double,
                    val yearlyInterestRate: Double,
                    val loanTermYears: Int) {

    val principal = amount - downPayment
    val monthlyInterestRates = (1+yearlyInterestRate/100).pow(1/12) -1
    val monthlyInterestRate = yearlyInterestRate/100.0/12.0
    val totalPaymentsDuration = loanTermYears*12
}