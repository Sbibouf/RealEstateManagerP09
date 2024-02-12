package com.example.realestatemanager.ui.estateList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.realestatemanager.R
import com.example.realestatemanager.data.local.service.Utils
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstatePhoto
import com.example.realestatemanager.model.EstateWithPhotos
import com.example.realestatemanager.ui.theme.EstateTheme
import kotlinx.coroutines.launch


@Composable
fun EstateItem(
    estateWithPhotos: EstateWithPhotos,
    onEstateClick: (EstateWithPhotos) -> Unit,
    imageSize: Int
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEstateClick(estateWithPhotos) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()

        ) {


            if (estateWithPhotos.photos?.size!! > 0) {
                AsyncImage(
                    model = estateWithPhotos.photos[0].uri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(imageSize.dp)
                        .padding(1.dp)
                        .weight(1f)
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.ic_no_photo_foreground),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .weight(1f)
                )
            }


            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            ) {
                //Type
                estateWithPhotos.estate?.type?.let {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Bold
                    )
                }

                //City
                estateWithPhotos.estate?.address?.let {
                    Text(
                        text = it
                    )
                }

                //Price
                estateWithPhotos.estate?.price?.let {
                    Text(
                        text = Utils.formatCurrency(it),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }

            }
            if (estateWithPhotos.estate?.soldState == false) {
                Image(
                    painter = painterResource(R.drawable.ic_not_sold_foreground),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)

                )
            } else {
                Image(
                    painter = painterResource(R.drawable.ic_sold_foreground),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)

                )
            }

        }
        HorizontalDivider()


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstateList(
    estateList: List<EstateWithPhotos>,
    onEstateClick: (EstateWithPhotos) -> Unit,
    onAddClick: () -> Unit,
    onSearchClick: () -> Unit,
    onDrawerLoanClick: () -> Unit,
    onDrawerMapClick: () -> Unit,
    onCancelSearchClick: () -> Unit,
    searchPerformed: Boolean,
    imageSize: Int
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()


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

                    IconButton(onClick = { onSearchClick() }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }

                }
            )
        }) { innerPadding ->
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(
                    onDrawerLoanClick = { onDrawerLoanClick() },
                    onDrawerMapClick = { onDrawerMapClick() })
            }, gesturesEnabled = false,
            modifier = Modifier.padding(innerPadding)
        ) {
            if (estateList.isEmpty() && searchPerformed) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Il n'y a aucun bien correspondant à la recherche")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { onCancelSearchClick() },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Ok")
                    }
                }

            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    if (searchPerformed) {
                        item(1) {
                            Row {
                                IconButton(onClick = { onCancelSearchClick() }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "cancel search"
                                    )
                                }
                                Text(
                                    text = "Annuler",
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }

                        }
                    }
                    items(estateList) { estate ->
                        EstateItem(estate, onEstateClick = onEstateClick, imageSize = imageSize)
                    }
                }
            }

        }
    }

}

@Composable
fun DrawerContent(onDrawerLoanClick: () -> Unit, onDrawerMapClick: () -> Unit) {
    Column(
        modifier = Modifier
            .background(Color.Gray)
            .fillMaxHeight()
    ) {
        Text(
            text = "Simulateur",
            color = Color.White,
            modifier = Modifier
                .clickable { onDrawerLoanClick() }
                .padding(16.dp)
        )
        Text(
            text = "Carte",
            color = Color.White,
            modifier = Modifier
                .clickable { onDrawerMapClick() }
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
fun EstateListPreview() {
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
                "", "",
                false,
                "",
                "",
                "", false, false, false, false, false, false, 1L
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
                "", "",
                false,
                "",
                "",
                "", false, false, false, false, false, false, 1L
            ),
            listOf(
                EstatePhoto(1L, "/storage/emulated/0/Download/estate2_front.jpg", "Façade"),
                EstatePhoto(1L, "/storage/emulated/0/Download/estate2_living.jpg", "Salon")
            )
        )
    )

    EstateTheme {

        EstateList(
            estatesTest,
            onEstateClick = {},
            onAddClick = {},
            onDrawerLoanClick = {},
            onDrawerMapClick = {},
            onSearchClick = {},
            onCancelSearchClick = {},
            searchPerformed = false,
            imageSize = 150
        )
    }


}

