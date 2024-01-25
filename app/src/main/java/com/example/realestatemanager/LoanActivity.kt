package com.example.realestatemanager

import LoanViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.realestatemanager.ui.loan.LoanSimulatorScreen
import com.example.realestatemanager.ui.theme.EstateTheme

class LoanActivity : ComponentActivity() {

    private val viewModel : LoanViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EstateTheme {
                LoanSimulatorScreen(viewModel = viewModel)
            }
        }
    }
}