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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstatePhoto
@Composable
fun EstateUiPortrait(estateList: List<Estate>, onEstateClick: (Estate) -> Unit, onAddClick: ()->Unit, modifier: Modifier){

   // EstateList(estateList = estateList, onEstateClick = onEstateClick, onAddClick = onAddClick, modifier = modifier )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstateUiLandscape(estate : Estate, estateList : List<Estate>, onEstateClick: (Estate) -> Unit){
    Log.d("EstateListAndDetail", "Recomposing...")
//    Scaffold(
//        topBar = {
//            TopAppBar( colors = TopAppBarDefaults.topAppBarColors(
//                containerColor = Color.Gray,
//                titleContentColor = Color.White,
//                navigationIconContentColor = Color.White,
//                actionIconContentColor = Color.White
//            ),
//                title = {
//                    Text(text = "Real Estate Manager", style = TextStyle.Default.copy(fontSize = 16.sp))
//                },
//                navigationIcon = {
//                    IconButton(onClick = { /* Handle navigation icon click */ }) {
//                        Icon(imageVector = Icons.Default.Menu, contentDescription = null)
//                    }
//                },
//                actions = {
//
//                    IconButton(onClick = { }) {
//                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
//                    }
//                    IconButton(onClick = { /* Handle settings icon click */ }) {
//                        Icon(imageVector = Icons.Default.Create, contentDescription = null)
//                    }
//                    IconButton(onClick = { /* Handle settings icon click */ }) {
//                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
//                    }
//
//                }
//            )
//        }, content = {Row(modifier = Modifier
//            .fillMaxSize()
//            .horizontalScroll(rememberScrollState()).padding(it)) {
//            LazyColumn(modifier = Modifier.weight(1f)) {
//                items(estateList) { estate ->
//                    EstateItem(estate, onEstateClick = onEstateClick)
//                }
//            }
//            Column(modifier = Modifier.weight(2f)) {
//                Spacer(modifier = Modifier.height(8.dp))
//                EstateMediaRow(estate)
//                EstateDescriptionRow(estate)
//                EstateDetailsRow(estate)
//
//            }
//
//        }})
}

@Preview(showSystemUi = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
fun Test(){

//    val estatesTest = listOf(
//        Estate("House","$100,000","300m2","5","3","1","", listOf(EstatePhoto("uri","nom")),"New York","","","","","",""),
//        Estate("Penthouse","$220,000","320m2","6","3","2","",listOf(EstatePhoto("uri","nom")),"Washington","","","","","","")
//    )
//    val estate = Estate("House","$100,000","300m2","5","3","1","Ce petit texte décrit le bien immobilier",
//        listOf(EstatePhoto("uri","nom")),"New York","","","","","","")
//    EstateUiLandscape(estate = estate , estateList = estatesTest, onEstateClick ={} )
}