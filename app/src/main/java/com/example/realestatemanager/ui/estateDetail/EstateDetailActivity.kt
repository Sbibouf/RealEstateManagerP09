package com.example.realestatemanager.ui.estateDetail

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.core.content.IntentCompat
import com.example.realestatemanager.model.EstateWithPhotos
import com.example.realestatemanager.ui.addEstate.AddEstateActivity
import com.example.realestatemanager.ui.theme.EstateDetailTheme
import com.example.realestatemanager.ui.theme.EstateDetailThemeTab


class EstateDetailActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val estate: EstateWithPhotos? =
            IntentCompat.getParcelableExtra(intent, "estate", EstateWithPhotos::class.java)

        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)

            when (windowSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Compact -> {
                    EstateDetailTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            if (estate != null) {
                                EstateDetailsScreen(estate, onBackClick = { finish() }, onModifyClick = {
                                    handleOnModifyClick(estate)
                                    finish()
                                }, modifier = Modifier,
                                    imageSize = 150,
                                    imageNameSize = 12)
                            }

                        }
                    }
                }
                WindowWidthSizeClass.Medium -> {
                    EstateDetailThemeTab {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            if (estate != null) {
                                EstateDetailsScreen(estate, onBackClick = { finish() }, onModifyClick = {
                                    handleOnModifyClick(estate)
                                    finish()
                                }, modifier = Modifier,
                                    imageSize = 300,
                                    imageNameSize = 20)
                            }

                        }
                    }
                }
                WindowWidthSizeClass.Expanded -> {
                    EstateDetailThemeTab {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            if (estate != null) {
                                EstateDetailsScreen(estate, onBackClick = { finish() }, onModifyClick = {
                                    handleOnModifyClick(estate)
                                    finish()
                                }, modifier = Modifier,
                                    imageSize = 300,
                                    imageNameSize = 20)
                            }

                        }
                    }
                }
            }

        }
    }


    private fun handleOnModifyClick(estate: EstateWithPhotos) {
        val intent = Intent(this, AddEstateActivity::class.java)
        intent.putExtra("estate", estate)
        startActivity(intent)
    }
}

