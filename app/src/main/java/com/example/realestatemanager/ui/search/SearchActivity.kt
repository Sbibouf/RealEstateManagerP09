package com.example.realestatemanager.ui.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.realestatemanager.model.SearchCriteria
import com.example.realestatemanager.ui.theme.EstateDetailTheme

class SearchActivity : ComponentActivity() {
    private val searchCriteria: SearchCriteria = SearchCriteria()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            EstateDetailTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SearchCriteria(searchCriteria = searchCriteria, onBackClick = {finish()})

                }
            }
        }
    }
}