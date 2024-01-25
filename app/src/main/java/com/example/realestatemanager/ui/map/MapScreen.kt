package com.example.realestatemanager.ui.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.realestatemanager.ui.estateList.EstateDescriptionRow
import com.example.realestatemanager.ui.estateList.EstateDetailsRow
import com.example.realestatemanager.ui.estateList.EstateMediaRow
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(onBackClick: ()->Unit) {

    val newYork = LatLng(40.7127744, -74.006059)
    val newYokState = MarkerState(position = newYork)
    val cameraPositionState = rememberCameraPositionState() {
        position = CameraPosition.fromLatLngZoom(newYork, 10f)
    }

    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Gray,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
            ),
                title = { Text(
                    text = "Retour",
                    style = TextStyle.Default.copy(fontSize = 20.sp)
                ) },
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

                Marker(
                    state = newYokState,
                    title = "New York"
                )

            }
        }
    )
}

@Preview
@Composable
fun MapPreview() {
    MapScreen(onBackClick = {})


}