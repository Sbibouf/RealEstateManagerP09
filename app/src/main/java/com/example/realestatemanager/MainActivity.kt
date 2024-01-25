package com.example.realestatemanager

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstateWithPhotos
import com.example.realestatemanager.ui.estateList.EstateListViewModel
import com.example.realestatemanager.ui.estateList.EstateUiPortrait
import com.example.realestatemanager.ui.theme.EstateTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val estateViewModel: EstateListViewModel by viewModels()
    private var isPermissionGranted by mutableStateOf(false)
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                isPermissionGranted = isGranted
                setupContent()
            } else {
                finish()
            }

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val test: Uri = Uri.fromFile(File("/storage/emulated/0/Download/estate1_front.jpg"))
        Log.d("Test", "$test")

        //Ask for permission to user depending on Android SDK Version
        if (!isPermissionGranted) {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                    requestPermissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
                }

                else -> {
                    requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        } else {
            setupContent()
        }

    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    private fun setupContent() {
        setContent {

            val windowSizeClass = calculateWindowSizeClass(this)
            val estateList = estateViewModel.uiState.collectAsState().value
            var selectedEstate by remember { mutableStateOf<Estate?>(null) }



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
                                onDrawerItemClick = {
                                    handleDrawerLoanClick()
                                },
                                modifier = Modifier
                            )
                        }

                        WindowWidthSizeClass.Expanded -> {
                            Log.d("EstateListAndDetail", "Recomposing...")
                            // EstateUiLandscape(estateWithPhotosList = estateList,estateWithPhotos = estateList[0], lat = LatLng(2323,23232),  onEstateClick = {})
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
}



