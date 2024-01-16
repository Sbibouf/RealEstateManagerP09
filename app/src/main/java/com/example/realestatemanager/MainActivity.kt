package com.example.realestatemanager

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.realestatemanager.data.local.repository.EstateRepository
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.ui.theme.KotlinTestTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val estateViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, SecondActivity::class.java)
        setContent {

            val estate = estateViewModel.uiState.collectAsState().value
            KotlinTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    EstateList(estate) { clickedEstate ->
                        handleEstateItemClick(clickedEstate)
                    }

                }
            }
        }
    }

    private fun handleEstateItemClick(clickedEstate: Estate) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("estate_id", clickedEstate)
        startActivity(intent)
    }
}



