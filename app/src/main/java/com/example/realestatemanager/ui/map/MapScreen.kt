package com.example.realestatemanager.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstateWithPhotos
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(onBackClick: () -> Unit, estateList : List<EstateWithPhotos>, onEstateClick: (EstateWithPhotos) -> Unit) {

    val newYork = LatLng(40.7127744, -74.006059)
    val cameraPositionState = rememberCameraPositionState() {
        position = CameraPosition.fromLatLngZoom(newYork, 8f)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Gray,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                ),
                title = {
                    Text(
                        text = "Carte des biens",
                        style = TextStyle.Default.copy(fontSize = 20.sp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },

                )
        },
        content = {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                cameraPositionState = cameraPositionState
            ) {
                for(estate : EstateWithPhotos in estateList){
                    if(estate.estate?.latitude != ""){
                        val latitude = estate.estate?.latitude?.toDoubleOrNull()
                        val longitude = estate.estate?.longitude?.toDoubleOrNull()
                        if(latitude!=null && longitude!=null){
                            val latLng = LatLng(latitude, longitude)
                            val position = MarkerState(position = latLng)
                            Marker(state = position, title = estate.estate.type, onInfoWindowClick = {onEstateClick(estate)})
                        }

                    }
                }

            }
        }
    )
}

@Preview
@Composable
fun MapPreview() {
    MapScreen(onBackClick = {}, estateList = emptyList(), onEstateClick = {})


}