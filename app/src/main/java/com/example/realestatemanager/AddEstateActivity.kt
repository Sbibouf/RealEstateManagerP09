package com.example.realestatemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.content.IntentCompat
import androidx.lifecycle.lifecycleScope
import com.example.realestatemanager.model.EstateWithPhotos
import com.example.realestatemanager.ui.addEstate.AddEstate
import com.example.realestatemanager.ui.addEstate.AddEstateViewModel
import com.example.realestatemanager.ui.theme.EstateDetailTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEstateActivity : ComponentActivity() {
    private val addEstateViewModel: AddEstateViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.hasExtra("estate")){
            val estateExtra : EstateWithPhotos? =
            IntentCompat.getParcelableExtra(intent, "estate", EstateWithPhotos::class.java)
            if (estateExtra != null) {
                addEstateViewModel.setEstateWithPhoto(estateExtra)
            }
        }
        setContent {
            val vmEstate = addEstateViewModel.estate.collectAsState().value
            val vmPhotoList = addEstateViewModel.photoList.collectAsState().value
            EstateDetailTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AddEstate(
                        onAddEstateClick = { estate, photoList ->
                            lifecycleScope.launch {
                                addEstateViewModel.geocodeAndInserts(estate, photoList)
                            }
                            finish()
                        },
                        estate = vmEstate,
                        onUpdateEstate = addEstateViewModel::updateEstate,
                        onBackClick = { finish() },
                        photoList = vmPhotoList,
                        onAddPhotoButtonClick = addEstateViewModel::addPhoto,
                        onChangePhotoButtonClick = addEstateViewModel::replaceOldPhotoByNewPhoto,
                        onDeletePhotoClick = addEstateViewModel::deletePhoto
                    )

                }
            }
        }
    }

}