package com.example.realestatemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.IntentCompat
import com.example.realestatemanager.model.EstateWithPhotos
import com.example.realestatemanager.ui.estateList.EstateDetailsScreen
import com.example.realestatemanager.ui.estateList.GeocodingViewModel
import com.example.realestatemanager.ui.theme.EstateDetailTheme


class EstateDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val estate: EstateWithPhotos? =
            IntentCompat.getParcelableExtra(intent, "estate", EstateWithPhotos::class.java)

        setContent {

            EstateDetailTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (estate != null ) {
                        EstateDetailsScreen(estate, onBackClick = { finish() }, onModifyClick = {}, modifier = Modifier)
                    }

                }
            }
        }
    }
}

