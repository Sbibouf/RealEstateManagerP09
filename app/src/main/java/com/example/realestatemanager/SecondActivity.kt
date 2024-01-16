package com.example.realestatemanager

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.IntentCompat
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.ui.theme.EstateTheme


class SecondActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val estate : Estate? = IntentCompat.getParcelableExtra(intent, "estate_id", Estate::class.java)
        setContent {
            EstateTheme {
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