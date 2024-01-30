package com.example.realestatemanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.realestatemanager.data.local.service.Utils
import com.example.realestatemanager.model.EstateWithPhotos
import com.example.realestatemanager.ui.estateList.EstateListViewModel
import com.example.realestatemanager.ui.estateList.EstateUiLandscape
import com.example.realestatemanager.ui.estateList.EstateUiPortrait
import com.example.realestatemanager.ui.theme.EstateTheme
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val estateViewModel: EstateListViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val windowSizeClass = calculateWindowSizeClass(this)
            val estateList = estateViewModel.uiState.collectAsState().value
            val estateWithPhoto = estateViewModel.uiStateLandscape.collectAsState().value
            //var selectedEstate by remember { mutableStateOf<Estate?>(null) }


            EstateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (windowSizeClass.widthSizeClass) {
                        WindowWidthSizeClass.Compact -> {
                            Log.d("EstateList", "Recomposing...")
                            EstateUiPortrait(
                                estateWithPhotosList = estateList,
                                onEstateClick = { clickedEstate ->
                                    handleEstateItemClick(clickedEstate)
                                },
                                onAddClick = {
                                    handleAddClick()
                                },
                                onDrawerLoanClick = {
                                    handleDrawerLoanClick()
                                },
                                onDrawerMapClick = {
                                    handleDrawerMapClick()
                                },
                                onSearchClick = {

                                },
                                modifier = Modifier
                            )
                        }

                        WindowWidthSizeClass.Expanded -> {
                            Log.d("EstateListAndDetail", "Recomposing...")
                            if (estateWithPhoto != null) {
                                EstateUiLandscape(
                                    estateWithPhotosList = estateList,
                                    estateWithPhotos = estateWithPhoto,
                                    lat = LatLng(2323.0, 23232.0),
                                    onEstateClick = {})
                            }
                        }

                        WindowWidthSizeClass.Medium -> {
                            Log.d("EstateListAndDetail", "Recomposing...")
                            EstateUiPortrait(
                                estateWithPhotosList = estateList,
                                onEstateClick = { clickedEstate ->
                                    handleEstateItemClick(clickedEstate)
                                },
                                onAddClick = {
                                    handleAddClick()
                                },
                                onDrawerLoanClick = {
                                    handleDrawerLoanClick()
                                },
                                onDrawerMapClick = {
                                    handleDrawerMapClick()
                                },
                                onSearchClick = {

                                },
                                modifier = Modifier
                            )
                        }
                    }

                }
            }
        }
    }

    private fun handleEstateItemClick(clickedEstate: EstateWithPhotos) {
        val intent = Intent(this, EstateDetailActivity::class.java)
        intent.putExtra("estate", clickedEstate)
        startActivity(intent)
    }

    private fun handleAddClick() {
        val intent = Intent(this, AddEstateActivity::class.java)
        startActivity(intent)
    }

    private fun handleDrawerLoanClick() {
        val intent = Intent(this, LoanActivity::class.java)
        startActivity(intent)
    }

    private fun handleDrawerMapClick() {
        val intent = Intent(this, MapActivity::class.java)
        if(Utils.isInternetAvailable(applicationContext)){
            startActivity(intent)
        }
        else {
            Toast.makeText(applicationContext, "Vous n'avez pas accès a internet", Toast.LENGTH_SHORT).show()
        }

    }

    private fun handleSearchClick(){
        //intent
        //startActivity
    }
}



