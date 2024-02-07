package com.example.realestatemanager.ui.estateList

import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstatePhoto
import com.example.realestatemanager.model.EstateWithPhotos
import com.example.realestatemanager.ui.estateDetail.EstateDescriptionRow
import com.example.realestatemanager.ui.estateDetail.EstateDetailsRow
import com.example.realestatemanager.ui.estateDetail.EstateMap
import com.example.realestatemanager.ui.estateDetail.EstateMediaRow
import kotlinx.coroutines.launch

@Composable
fun EstateUiPortrait(
    estateWithPhotosList: List<EstateWithPhotos>,
    onEstateClick: (EstateWithPhotos) -> Unit,
    onAddClick: () -> Unit,
    onDrawerLoanClick: () -> Unit,
    onDrawerMapClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier
) {

    EstateList(
        estateList = estateWithPhotosList,
        onEstateClick = onEstateClick,
        onAddClick = onAddClick,
        onDrawerLoanClick = onDrawerLoanClick,
        onDrawerMapClick = onDrawerMapClick,
        onSearchClick = onSearchClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstateUiLandscape(
    estateWithPhotosList: List<EstateWithPhotos>,
    estateWithPhotos: EstateWithPhotos,
    onEstateClick: (EstateWithPhotos) -> Unit,
    onAddClick: () -> Unit,
    onDrawerLoanClick: () -> Unit,
    onDrawerMapClick: () -> Unit,
    onSearchClick: () -> Unit,
    onModifyClick: () -> Unit,
    modifier: Modifier
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    Log.d("EstateListAndDetail", "Recomposing...")
    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Gray,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White
            ),
                title = {
                    Text(
                        text = "Real Estate Manager",
                        style = TextStyle.Default.copy(fontSize = 16.sp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                    }
                },
                actions = {

                    IconButton(onClick = { onAddClick() }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                    IconButton(onClick = { onModifyClick() }) {
                        Icon(imageVector = Icons.Default.Create, contentDescription = null)
                    }
                    IconButton(onClick = { onSearchClick() }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }

                }
            )
        }, content = {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    DrawerContent(
                        onDrawerLoanClick = { onDrawerLoanClick() },
                        onDrawerMapClick = { onDrawerMapClick() })
                }, gesturesEnabled = false,
                modifier = Modifier.padding(it)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(rememberScrollState())

                ) {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(estateWithPhotosList) { estate ->
                            EstateItem(estate, onEstateClick = onEstateClick)
                        }
                    }

                    if (estateWithPhotos != EstateWithPhotos()) {
                        Column(
                            modifier = Modifier
                                .weight(2f)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Spacer(modifier = Modifier.height(8.dp))
                            EstateMediaRow(estateWithPhotos)
                            EstateDescriptionRow(estateWithPhotos)
                            Row(modifier = Modifier.fillMaxWidth()) {
                                EstateDetailsRow(estateWithPhotos)
                                EstateMap(estateWithPhotos)
                            }


                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .weight(2f)
                                .align(Alignment.CenterVertically),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            NoEstateWithPhoto()
                        }
                    }


                }
            }
        })
}


@Composable
fun NoEstateWithPhoto() {

    Column{
        Text(text = "Veuillez selectionner un bien immobilier", fontSize = 30.sp)
    }
}


@Preview(
    showSystemUi = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
fun Test() {

    val estatesTest = listOf(
        EstateWithPhotos(
            Estate(
                "House",
                "$100,000",
                "300m2",
                "5",
                "3",
                "1",
                "",
                "New York",
                "",
                "",
                "",
                false,
                "",
                "",
                "",
                false,
                false,
                false,
                false,
                false,
                false
            ),
            listOf(
                EstatePhoto(1L, "/storage/emulated/0/Download/estate1_front.jpg", "Façade"),
                EstatePhoto(1L, "/storage/emulated/0/Download/estate1_living.jpg", "Salon")
            )
        ),
        EstateWithPhotos(
            Estate(
                "PentHouse",
                "$100,000,000",
                "300m2",
                "5",
                "3",
                "1",
                "",
                "New York",
                "",
                "",
                "",
                false,
                "",
                "",
                "",
                false,
                false,
                false,
                false,
                false,
                false
            ),
            listOf(
                EstatePhoto(1L, "/storage/emulated/0/Download/estate2_front.jpg", "Façade"),
                EstatePhoto(1L, "/storage/emulated/0/Download/estate2_living.jpg", "Salon")
            )
        )
    )

    val estateWithPhotoTest = EstateWithPhotos(
        Estate(
            "House",
            "$100,000",
            "300m2",
            "5",
            "3",
            "1",
            "",
            "New York",
            "",
            "",
            "",
            false,
            "",
            "",
            "",
            false,
            false,
            false,
            false,
            false,
            false
        ),
        listOf(
            EstatePhoto(1L, "/storage/emulated/0/Download/estate1_front.jpg", "Façade"),
            EstatePhoto(1L, "/storage/emulated/0/Download/estate1_living.jpg", "Salon")
        )
    )

    //EstateUiLandscape(estateWithPhotos = estateWithPhotoTest , estateWithPhotosList = estatesTest, onEstateClick ={}, onDrawerLoanClick = {}, onDrawerMapClick = {}, onAddClick = {}, onSearchClick = {}, modifier = Modifier )
}