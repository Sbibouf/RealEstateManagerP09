package com.example.realestatemanager

import EstateDetailsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.ui.theme.KotlinTestTheme

class SecondActivity : ComponentActivity() {

    val estate : Estate? = intent.getParcelableExtra("estate_id")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    if(estate!= null){
                        EstateDetailsScreen(estate)
                    }


                }
            }
        }
    }
}