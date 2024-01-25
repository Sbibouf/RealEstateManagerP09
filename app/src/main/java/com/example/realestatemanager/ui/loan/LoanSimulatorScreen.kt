package com.example.realestatemanager.ui.loan

import LoanViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.realestatemanager.model.LoanData
import java.text.DecimalFormat
import kotlin.math.pow


@Composable
fun LoanSimulatorScreen(
    viewModel: LoanViewModel
) {
    var amount by remember { mutableStateOf("") }
    var downPayment by remember { mutableStateOf("") }
    var interestRate by remember { mutableStateOf("") }
    var loanTerm by remember { mutableStateOf("") }
    val decimalFormat = DecimalFormat("#.###")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Loan Amount") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = downPayment,
            onValueChange = { downPayment = it },
            label = { Text("Down Payment") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = interestRate,
            onValueChange = { interestRate = it },
            label = { Text("Interest Rate (Yearly)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = loanTerm,
            onValueChange = { loanTerm = it },
            label = { Text("Loan Term (Years)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val loanData = LoanData(
                amount.toDouble(),
                downPayment.toDouble(),
                interestRate.toDouble(),
                loanTerm.toInt()
            )
            viewModel.updateLoanData(loanData)
        }) {
            Text("Calculate")
        }

        val currentLoanData by viewModel.loanData.collectAsState()
        Text("Monthly Payment: ${calculateLoanDetail(currentLoanData, 1)}")
        Text("Total Interest: ${calculateLoanDetail(currentLoanData, 2)}")
        Text("Total Payment: ${calculateLoanDetail(currentLoanData, 3)}")

    }
}

private fun calculateLoanDetail(loanData: LoanData, type: Int): Double {
    val principal = loanData.amount - loanData.downPayment
    //val monthlyInterestRates = (1 + loanData.yearlyInterestRate / 100).pow(1 / 12) - 1
    val monthlyInterestRate = loanData.yearlyInterestRate / 100.0 / 12.0
    val totalPaymentsDuration = loanData.loanTermYears * 12
    val interest =
        principal * (monthlyInterestRate) / (1 - (1 + monthlyInterestRate).pow(-totalPaymentsDuration.toDouble()))


    when (type) {
        1 -> return interest
        2 -> return (interest * totalPaymentsDuration) - principal
        3 -> return (interest * totalPaymentsDuration)
    }
    return 0.0
}


@Preview
@Composable
fun Test() {
    // LoanSimulatorScreen()
}
