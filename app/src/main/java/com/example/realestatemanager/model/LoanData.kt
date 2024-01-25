package com.example.realestatemanager.model

data class LoanData(val amount: Double,
                    val downPayment: Double,
                    val yearlyInterestRate: Double,
                    val loanTermYears: Int)