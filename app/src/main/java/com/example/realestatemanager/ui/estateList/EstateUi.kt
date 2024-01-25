package com.example.realestatemanager.ui.estateList
import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstatePhoto
import com.example.realestatemanager.model.EstateWithPhotos
import com.google.android.gms.maps.model.LatLng

@Composable
fun EstateUiPortrait(estateWithPhotosList: List<EstateWithPhotos>, onEstateClick: (EstateWithPhotos) -> Unit, onAddClick: ()->Unit, onDrawerLoanClick: ()->Unit, onDrawerMapClick: ()->Unit, modifier: Modifier){

    EstateList(estateList = estateWithPhotosList, onEstateClick = onEstateClick, onAddClick = onAddClick, onDrawerLoanClick = onDrawerLoanClick, onDrawerMapClick = onDrawerMapClick, modifier = modifier )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstateUiLandscape( estateWithPhotosList : List<EstateWithPhotos>,estateWithPhotos : EstateWithPhotos, lat : LatLng?, onEstateClick: (EstateWithPhotos) -> Unit){
    Log.d("EstateListAndDetail", "Recomposing...")
    Scaffold(
        topBar = {
            TopAppBar( colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Gray,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White
            ),
                title = {
                    Text(text = "Real Estate Manager", style = TextStyle.Default.copy(fontSize = 16.sp))
                },
                navigationIcon = {
                    IconButton(onClick = { /* Handle navigation icon click */ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                    }
                },
                actions = {

                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                    IconButton(onClick = { /* Handle settings icon click */ }) {
                        Icon(imageVector = Icons.Default.Create, contentDescription = null)
                    }
                    IconButton(onClick = { /* Handle settings icon click */ }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }

                }
            )
        }, content = {
            Row(modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(rememberScrollState()).padding(it)) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(estateWithPhotosList) { estate ->
                    EstateItem(estate, onEstateClick = onEstateClick)
                }
            }
            Column(modifier = Modifier.weight(2f)
                .verticalScroll(rememberScrollState())) {
                Spacer(modifier = Modifier.height(8.dp))
                EstateMediaRow(estateWithPhotos)
                EstateDescriptionRow(estateWithPhotos)
                EstateDetailsRow(estateWithPhotos, lat)

            }

        }})
}

@Preview(showSystemUi = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
fun Test(){

    val estatesTest = listOf(
        EstateWithPhotos(Estate("House","$100,000","300m2","5","3","1","","New York","","","","","",""),
            listOf( EstatePhoto(1L,"/storage/emulated/0/Download/estate1_front.jpg", "Façade"),
                EstatePhoto(1L,"/storage/emulated/0/Download/estate1_living.jpg","Salon"))),
        EstateWithPhotos(Estate("PentHouse","$100,000,000","300m2","5","3","1","","New York","","","","","",""),
            listOf( EstatePhoto(1L,"/storage/emulated/0/Download/estate2_front.jpg", "Façade"),
                EstatePhoto(1L,"/storage/emulated/0/Download/estate2_living.jpg","Salon"))))

    val estateWithPhotoTest = EstateWithPhotos(Estate("House","$100,000","300m2","5","3","1","","New York","","","","","",""),
        listOf( EstatePhoto(1L,"/storage/emulated/0/Download/estate1_front.jpg", "Façade"),
            EstatePhoto(1L,"/storage/emulated/0/Download/estate1_living.jpg","Salon")))

    //EstateUiLandscape(estateWithPhotos = estateWithPhotoTest , estateWithPhotosList = estatesTest, onEstateClick ={} )
}