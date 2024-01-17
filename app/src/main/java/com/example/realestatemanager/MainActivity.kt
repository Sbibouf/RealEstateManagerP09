package com.example.realestatemanager

import android.content.Intent
import android.os.Bundle
import android.service.autofill.OnClickAction
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.ui.theme.EstateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val estateViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val estate = estateViewModel.uiState.collectAsState().value
            EstateTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    EstateList(estate = estate,
                        onEstateClick = { clickedEstate ->
                            handleEstateItemClick(clickedEstate)
                        },
                        onAddClick = {
                            handleAddClick()
                        }) 

                }
            }
        }
    }

    private fun handleEstateItemClick(clickedEstate: Estate) {
        val intent = Intent(this, EstateDetailActivity::class.java)
        intent.putExtra("estate", clickedEstate)
        startActivity(intent)
    }

    private fun handleAddClick(){
        val intent = Intent(this, AddEstateActivity::class.java)
        startActivity(intent)
    }
}



