package com.example.realestatemanager.ui.loan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.realestatemanager.R
import com.example.realestatemanager.model.LoanData
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import java.text.DecimalFormat
import kotlin.math.pow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanSimulatorScreen(onBackClick : ()->Unit) {
    var amount by remember { mutableStateOf("") }
    var downPayment by remember { mutableStateOf("") }
    var interestRate by remember { mutableStateOf("") }
    var loanTerm by remember { mutableStateOf("") }
    var loandata by remember { mutableStateOf<LoanData?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Gray,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                ),
                title = {
                    Text(
                        text = "Retour",
                        style = TextStyle.Default.copy(fontSize = 20.sp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },

                )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.Simulateur))
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Montant emprunté") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = downPayment,
                    onValueChange = { downPayment = it },
                    label = { Text("Apport") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = interestRate,
                    onValueChange = { interestRate = it.replace(",",".") },
                    label = { Text("Taux d'interet annuel") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = loanTerm,
                    onValueChange = { loanTerm = it },
                    label = { Text("Durée du prêt") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    if(amount==""){
                        amount = "0"
                    }
                    else if (downPayment==""){
                        downPayment="0"
                    }
                    else if (interestRate==""){
                        interestRate="0"
                    }
                    else if(loanTerm==""){
                        loanTerm="0"
                    }
                    val data = LoanData(
                        amount.toDouble(),
                        downPayment.toDouble(),
                        interestRate.toDouble(),
                        loanTerm.toInt()
                    )
                    loandata = data
                }) {
                    Text("Calculate")
                }

                Text("Mensualités: ${loandata?.let { calculateLoanDetail(it, "monthly") }} $")
                Text("Coût des intérêts: ${loandata?.let { calculateLoanDetail(it, "interest") }} $")
                Text("Coût total du prêt: ${loandata?.let { calculateLoanDetail(it, "total") }} $")


            }
        }
    )


}

private fun calculateLoanDetail(loanData: LoanData, type: String): String {
    val decimalFormat = DecimalFormat("#.##")
    val principal = loanData.amount - loanData.downPayment
    //val monthlyInterestRates = (1 + loanData.yearlyInterestRate / 100).pow(1 / 12) - 1
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


@Preview
@Composable
fun Test() {
   LoanSimulatorScreen(onBackClick = {})
}
