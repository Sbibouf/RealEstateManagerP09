package com.example.realestatemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.realestatemanager.ui.loan.LoanSimulatorScreen
import com.example.realestatemanager.ui.theme.EstateTheme

class LoanActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EstateTheme {
                LoanSimulatorScreen(onBackClick = {finish()})
            }
        }
    }
}