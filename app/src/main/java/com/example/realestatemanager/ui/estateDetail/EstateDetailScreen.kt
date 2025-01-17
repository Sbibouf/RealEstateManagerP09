package com.example.realestatemanager.ui.estateDetail

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.realestatemanager.BuildConfig
import com.example.realestatemanager.R
import com.example.realestatemanager.data.local.service.Utils
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstatePhoto
import com.example.realestatemanager.model.EstateWithPhotos


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstateDetailsScreen(
    estateWithPhotos: EstateWithPhotos,
    onBackClick: () -> Unit,
    onModifyClick: () -> Unit,
    imageSize: Int,
    imageNameSize: Int,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Gray,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                title = { estateWithPhotos.estate?.type?.let { Text(text = it) } },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onModifyClick() }) {
                        Icon(imageVector = Icons.Default.Create, contentDescription = null)
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                EstateMediaRow(estateWithPhotos, imageSize, imageNameSize)
                EstateDescriptionRow(estateWithPhotos)
                EstateDetailsRow(estateWithPhotos)
                EstateMap(estateWithPhotos)

            }
        }
    )
}

@Composable
fun EstateMediaRow(estateWithPhotos: EstateWithPhotos, imageSize: Int, imageNameSize: Int) {
    val estatePhotos: List<EstatePhoto> = estateWithPhotos.photos ?: emptyList()
    Column(modifier = Modifier.padding(8.dp)) {

        Text(text = "Media")
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            LazyRow(
                modifier = Modifier
            ) {
                items(estatePhotos) { photo ->
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .width(imageSize.dp)
                    ) {
                        AsyncImage(
                            model = Uri.parse(photo.uri),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(imageSize.dp)
                                .padding(1.dp)
                                .clickable { }
                        )
                        photo.name?.let {
                            Text(
                                text = it,
                                color = Color.White,
                                fontSize = imageNameSize.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Black.copy(alpha = 0.5f))
                                    .padding(4.dp)
                                    .align(Alignment.BottomCenter)
                            )
                        }
                    }

                }
            }
        }
    }

}

@Composable
fun EstateDescriptionRow(estateWithPhotos: EstateWithPhotos) {

    Column {

        Text(text = "Description", modifier = Modifier.padding(horizontal = 8.dp))
        Spacer(modifier = Modifier.height(8.dp))
        estateWithPhotos.estate?.description?.let {
            Text(
                text = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}

@Composable
fun EstateDetailsRow(estateWithPhotos: EstateWithPhotos) {

    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.baseline_diamond_24),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Prix:")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = Utils.formatCurrency(estateWithPhotos.estate?.price),
                    fontWeight = FontWeight.Bold
                )


            }
            Spacer(modifier = Modifier.height(16.dp))
        }


        Row {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_aspect_ratio_24),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Surface:")
                    Spacer(modifier = Modifier.width(8.dp))
                    estateWithPhotos.estate?.size?.let {
                        Text(
                            text = it + "M²",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_bed_24),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Chambres:")
                    Spacer(modifier = Modifier.width(8.dp))
                    estateWithPhotos.estate?.numberOfBedrooms?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Arrivée:")
                    Spacer(modifier = Modifier.width(4.dp))
                    estateWithPhotos.estate?.entryDate?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

            }
            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Pièces:")
                    Spacer(modifier = Modifier.width(8.dp))
                    estateWithPhotos.estate?.numberOfRooms?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_bathtub_24),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Salles de bains:")
                    Spacer(modifier = Modifier.width(8.dp))
                    estateWithPhotos.estate?.numberOfBathrooms?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Vente:")
                    Spacer(modifier = Modifier.width(4.dp))
                    estateWithPhotos.estate?.soldDate?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

            }
        }


        Text(text = "Points d'interet à proximité :", modifier = Modifier.padding(vertical = 8.dp))
        Row {
            if (estateWithPhotos.estate?.school == true) {
                Icon(
                    painter = painterResource(R.drawable.ic_school_foreground),
                    modifier = Modifier.size(50.dp),
                    contentDescription = null
                )
            }
            if (estateWithPhotos.estate?.shops == true) {
                Icon(
                    painter = painterResource(R.drawable.ic_shop_foreground),
                    modifier = Modifier.size(50.dp),
                    contentDescription = null
                )
            }
            if (estateWithPhotos.estate?.parc == true) {
                Icon(
                    painter = painterResource(R.drawable.ic_parc_foreground),
                    modifier = Modifier.size(50.dp),
                    contentDescription = null
                )
            }
            if (estateWithPhotos.estate?.hospital == true) {
                Icon(
                    painter = painterResource(R.drawable.ic_hospital_foreground),
                    modifier = Modifier.size(50.dp),
                    contentDescription = null
                )
            }
            if (estateWithPhotos.estate?.restaurant == true) {
                Icon(
                    painter = painterResource(R.drawable.ic_restaurant_foreground),
                    modifier = Modifier.size(50.dp),
                    contentDescription = null
                )
            }
            if (estateWithPhotos.estate?.sport == true) {
                Icon(
                    painter = painterResource(R.drawable.ic_fitness_foreground),
                    modifier = Modifier.size(50.dp),
                    contentDescription = null
                )
            }
            if ((estateWithPhotos.estate?.school == false) && (estateWithPhotos.estate.shops == false) && (estateWithPhotos.estate.parc == false) && (estateWithPhotos.estate.hospital == false) && (estateWithPhotos.estate.restaurant == false) && (estateWithPhotos.estate.sport == false)) {
                Icon(
                    painter = painterResource(R.drawable.ic_none_foreground),
                    modifier = Modifier.size(50.dp),
                    contentDescription = null
                )
            }
        }

    }
}

@Composable
fun EstateMap(estateWithPhotos: EstateWithPhotos) {

    val api = BuildConfig.MAPS_API_KEY

    Column {
        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
            Text(text = "Localisation: ${estateWithPhotos.estate?.address}")
        }

        if (estateWithPhotos.estate?.latitude != "" && estateWithPhotos.estate?.latitude != null) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .width(300.dp)
                    .height(300.dp)
            ) {
                AsyncImage(
                    model = "https://maps.googleapis.com/maps/api/staticmap?zoom=15&size=300x300&scale=2&maptype=roadmap\\&markers=size:mid%7Ccolor:red%7C " + estateWithPhotos.estate.latitude + "," + estateWithPhotos.estate.longitude + "&center=" + estateWithPhotos.estate.latitude + "," + estateWithPhotos.estate.longitude + "&key=" + api,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .border(2.dp, Color.Gray)
                )
            }
        } else {
            Text(
                text = stringResource(id = R.string.Address_error),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                color = Color.Red
            )
        }
    }


}

@Preview
@Composable
fun EstateTest() {
    val estateWithPhotoTest = EstateWithPhotos(
        Estate(
            "House",
            "100000",
            "300m2",
            "5",
            "3",
            "1",
            "Ce texte décrit le bien immobilier",
            "New York",
            "",
            "",
            false,
            "21/12/2023",
            "31/12/2023",
            "",
            true,
            true,
            true,
            true,
            true,
            true, 1L
        ),
        listOf(
            EstatePhoto(1L, "/storage/emulated/0/Download/estate1_front.jpg", "Façade"),
            EstatePhoto(1L, "/storage/emulated/0/Download/estate1_living.jpg", "Salon")
        )
    )
    EstateDetailsScreen(
        estateWithPhotos = estateWithPhotoTest,
        onBackClick = {},
        onModifyClick = {},
        imageSize = 150,
        imageNameSize = 12
    )
}