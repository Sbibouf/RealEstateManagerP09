package com.example.realestatemanager.ui.search

import android.app.Activity
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
import com.example.realestatemanager.model.SearchCriteria
import com.example.realestatemanager.ui.theme.EstateDetailTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : ComponentActivity() {
    private val searchViewModel: SearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val search = searchViewModel.search.collectAsState().value

            EstateDetailTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SearchCriteria(
                        searchCriteria = search,
                        onBackClick = { finish() },
                        onUpdateSearch = searchViewModel::updateEstate,
                        onSearchClick = { data ->
                            val returnIntent = Intent()
                            returnIntent.putExtra("search", data)
                            setResult(Activity.RESULT_OK, returnIntent)
                            finish()
                        })

                }
            }
        }
    }
}