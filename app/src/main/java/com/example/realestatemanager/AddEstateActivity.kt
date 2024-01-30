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
import androidx.lifecycle.lifecycleScope
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.ui.addEstate.AddEstate
import com.example.realestatemanager.ui.addEstate.AddEstateViewModel
import com.example.realestatemanager.ui.estateList.GeocodingViewModel
import com.example.realestatemanager.ui.theme.EstateDetailTheme
import com.example.realestatemanager.ui.theme.EstateTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEstateActivity : ComponentActivity() {
    private val addEstateViewModel: AddEstateViewModel by viewModels()

    private val geocodingViewModel: GeocodingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val estate = addEstateViewModel.estate.collectAsState().value
            val photoList = addEstateViewModel.photoList.collectAsState().value
            EstateDetailTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AddEstate(onButtonClick = { data ->
                        lifecycleScope.launch {
                            performGeocodingAndInsert(data)
                        }

                        finish()
                    }, estate = estate, photoList = photoList)

                }
            }
        }
    }

    private suspend fun performGeocodingAndInsert(data: Estate) {
        // Perform geocoding
        geocodingViewModel.geocodeAddress(data.address)

        // Observe changes in the geocoding result
        geocodingViewModel.locationState.collect { location ->
            if(location!= null){
                addEstateViewModel.insertEstate(
                    data.type,
                    data.price,
                    data.size,
                    data.numberOfRooms,
                    data.numberOfBedrooms,
                    data.numberOfBathrooms,
                    data.description,
                    data.address,
                    data.city,
                    location.latitude.toString(),
                    location.longitude.toString(),
                    data.soldState,
                    data.entryDate,
                    data.soldDate,
                    data.agent,
                    data.school,
                    data.shops,
                    data.parc,
                    data.hospital,
                    data.restaurant,
                    data.sport
                )
            }
            else {
                addEstateViewModel.insertHoleEstate(data)
            }
            // Finish the activity
            finish()
        }
    }

}