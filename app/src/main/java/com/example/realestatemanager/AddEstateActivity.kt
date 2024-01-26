package com.example.realestatemanager

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.realestatemanager.ui.theme.EstateTheme
import com.example.realestatemanager.ui.addEstate.AddEstate
import com.example.realestatemanager.ui.addEstate.AddEstateViewModel
import com.example.realestatemanager.ui.estateList.GeocodingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEstateActivity : ComponentActivity() {
    private val addEstateViewModel: AddEstateViewModel by viewModels()

    private val geocodingViewModel: GeocodingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EstateTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AddEstate(onButtonClick = { data ->
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
                            data.placesOfInterest,
                            data.state,
                            data.entryDate,
                            data.soldDate,
                            data.agent
                        )
                        addEstateViewModel.updateEstate { copy(type = "test") }
                        finish()
                    }, onPhotoClick = {
                    })

                }
            }
        }
    }

}