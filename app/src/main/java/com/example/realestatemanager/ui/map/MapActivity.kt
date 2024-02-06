package com.example.realestatemanager.ui.map

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.example.realestatemanager.model.EstateWithPhotos
import com.example.realestatemanager.ui.estateDetail.EstateDetailActivity
import com.example.realestatemanager.ui.theme.EstateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapActivity : ComponentActivity() {

    private val mapViewModel: MapViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EstateTheme {
                val estateList = mapViewModel.uiStateMap.collectAsState().value
                MapScreen(
                    onBackClick = { finish() },
                    estateList = estateList,
                    onEstateClick = { clickedEstate -> handleEstateClicked(clickedEstate) })
            }
        }
    }

    private fun handleEstateClicked(clickedEstate: EstateWithPhotos) {
        val intent = Intent(this, EstateDetailActivity::class.java)
        intent.putExtra("estate", clickedEstate)
        startActivity(intent)
    }
}