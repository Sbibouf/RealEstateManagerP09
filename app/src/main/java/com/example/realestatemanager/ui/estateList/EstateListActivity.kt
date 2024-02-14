package com.example.realestatemanager.ui.estateList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.content.IntentCompat
import com.example.realestatemanager.data.local.service.Utils
import com.example.realestatemanager.model.EstateWithPhotos
import com.example.realestatemanager.model.SearchCriteria
import com.example.realestatemanager.ui.addEstate.AddEstateActivity
import com.example.realestatemanager.ui.estateDetail.EstateDetailActivity
import com.example.realestatemanager.ui.loan.LoanActivity
import com.example.realestatemanager.ui.map.MapActivity
import com.example.realestatemanager.ui.search.SearchActivity
import com.example.realestatemanager.ui.theme.EstateTheme
import com.example.realestatemanager.ui.theme.EstateThemeTab
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EstateListActivity : ComponentActivity() {

    private val estateViewModel: EstateListViewModel by viewModels()
    //private var searchSuccess : Boolean = false


    private val searchActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                //searchSuccess = true
                val data: Intent? = result.data
                val searchData: SearchCriteria? =
                    data?.let {
                        IntentCompat.getParcelableExtra(
                            it,
                            "search",
                            SearchCriteria::class.java
                        )
                    }
                if (searchData != null) {

                    estateViewModel.getEstateWithPhotoFromSearch(searchData)
                }
            }

        }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val windowSizeClass = calculateWindowSizeClass(this)
            val vmSearchPerformed = estateViewModel.searchPerformed.collectAsState().value
            val vmEstateList = estateViewModel.uiState.collectAsState().value
            val vmEstateSearchList = estateViewModel.estatePhotoList.collectAsState().value
            val vmEstateWithPhoto = estateViewModel.estateWithPhoto.collectAsState().value
            val estateWithPhotosList = if (vmSearchPerformed) {
                vmEstateSearchList
            } else {
                vmEstateList
            }

            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                when (windowSizeClass.widthSizeClass) {
                    WindowWidthSizeClass.Compact -> {
                        Log.d("EstateList", "Recomposing...")
                        EstateTheme {
                            EstateUiPortraitCompact(
                                estateWithPhotosList = estateWithPhotosList,
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
                                onCancelSearchClick = estateViewModel::cancelSearch,
                                searchPerformed = vmSearchPerformed,
                            )
                        }

                    }

                    WindowWidthSizeClass.Expanded -> {
                        Log.d("EstateListAndDetail", "Recomposing...")

                        EstateTheme {
                            EstateUiLandscape(
                                estateWithPhotosList = estateWithPhotosList,
                                estateWithPhotos = vmEstateWithPhoto,
                                onSearchClick = { handleSearchClick() },
                                onAddClick = { handleAddClick() },
                                onDrawerMapClick = { handleDrawerMapClick() },
                                onDrawerLoanClick = { handleDrawerLoanClick() },
                                onEstateClick = { clickedEstate ->
                                    setEstateWithPhoto(clickedEstate)
                                },
                                onModifyClick = { handleOnModifyClick(vmEstateWithPhoto) },
                                modifier = Modifier,
                                onCancelSearchClick = estateViewModel::cancelSearch,
                                searchPerformed = vmSearchPerformed
                            )
                        }

                    }

                    WindowWidthSizeClass.Medium -> {
                        Log.d("EstateListAndDetail", "Recomposing...")
                        EstateThemeTab {
                            EstateUiPortraitMedium(
                                estateWithPhotosList = estateWithPhotosList,
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
                                onCancelSearchClick = estateViewModel::cancelSearch,
                                searchPerformed = vmSearchPerformed,
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
        searchActivityResultLauncher.launch(intent)
    }

    private fun setEstateWithPhoto(estateWithPhotos: EstateWithPhotos) {
        estateViewModel.setEstateWithPhoto(estateWithPhotos)
    }

    private fun handleOnModifyClick(estate: EstateWithPhotos) {
        val intent = Intent(this, AddEstateActivity::class.java)
        intent.putExtra("estate", estate)
        startActivity(intent)
    }


//        this.textViewMain = findViewById(R.id.activity_second_activity_text_view_main);
//        this.textViewQuantity = findViewById(R.id.activity_main_activity_text_view_quantity);

//        private void configureTextViewQuantity(){
//        int quantity = Utils.convertDollarToEuro(100);
//        this.textViewQuantity.setTextSize(20);
//        this.textViewQuantity.setText(quantity);
//    }
}



