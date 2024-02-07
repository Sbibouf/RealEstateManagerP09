package com.example.realestatemanager.ui.estateList

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
import com.example.realestatemanager.ui.addEstate.AddEstateActivity
import com.example.realestatemanager.ui.estateDetail.EstateDetailActivity
import com.example.realestatemanager.ui.loan.LoanActivity
import com.example.realestatemanager.ui.map.MapActivity
import com.example.realestatemanager.ui.search.SearchActivity
import com.example.realestatemanager.ui.theme.EstateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EstateListActivity : ComponentActivity() {

    private val estateViewModel: EstateListViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val windowSizeClass = calculateWindowSizeClass(this)
            val estateList = estateViewModel.uiState.collectAsState().value
            val estateWithPhoto = estateViewModel.estateWithPhoto.collectAsState().value


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
                                    handleSearchClick()
                                },
                                modifier = Modifier
                            )
                        }

                        WindowWidthSizeClass.Expanded -> {
                            Log.d("EstateListAndDetail", "Recomposing...")

                            EstateUiLandscape(
                                estateWithPhotosList = estateList,
                                estateWithPhotos = estateWithPhoto,
                                onSearchClick = { handleSearchClick() },
                                onAddClick = { handleAddClick() },
                                onDrawerMapClick = { handleDrawerMapClick() },
                                onDrawerLoanClick = { handleDrawerLoanClick() },
                                onEstateClick = { clickedEstate ->
                                    setEstateWithPhoto(clickedEstate)
                                },
                                onModifyClick = { handleOnModifyClick(estateWithPhoto) },
                                modifier = Modifier
                            )
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
                                    handleSearchClick()
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
        if (Utils.isInternetAvailable(applicationContext)) {
            startActivity(intent)
        } else {
            Toast.makeText(
                applicationContext,
                "Vous n'avez pas accès a internet",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun handleSearchClick() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }

    private fun setEstateWithPhoto(estateWithPhotos: EstateWithPhotos) {
        estateViewModel.setEstateWithPhoto(estateWithPhotos)
    }

    private fun handleOnModifyClick(estate: EstateWithPhotos) {
        val intent = Intent(this, AddEstateActivity::class.java)
        intent.putExtra("estate", estate)
        startActivity(intent)
    }
}



