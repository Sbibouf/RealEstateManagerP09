package com.example.realestatemanager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.realestatemanager.model.Estate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstateDetailsScreen(estate: Estate) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { estate.type?.let { Text(text = it) } },
                navigationIcon = {
                    IconButton(onClick = { /* Handle navigation icon click */ }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },

            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                    EstateMediaRow(estate)
                    EstateDescriptionRow(estate)
                    EstateDetailsRow(estate)

            }
        }
    )
}

@Composable
fun EstateMediaRow(estate: Estate) {
    val estatesTest = listOf(
        Estate("House","$100,000","300m2",5,3,1,"blabla","https://images.pexels.com/photos/323780/pexels-photo-323780.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1","New York","","","","",""),
        Estate("Penthouse","$220,000","320m2",6,3,2,"","https://images.pexels.com/photos/53610/large-home-residential-house-architecture-53610.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1","Washington","","","","","")
    )
    Column(modifier = Modifier.padding(8.dp)){

        Text(text = "Media")
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Assuming estate.photos is a list of photo URLs
            LazyRow(modifier = Modifier
                ) {
                items(estatesTest) { photoUrl ->
                    AsyncImage(
                        model = estate.picture, // Placeholder image
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .padding(1.dp)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }
    }

}

@Composable
fun EstateDescriptionRow(estate: Estate) {

    Column(){

        Text(text = "Description", modifier = Modifier.padding(horizontal = 8.dp))
        estate.description?.let {
            Text(
                text = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Composable
fun EstateDetailsRow(estate: Estate) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row {
            Icon(painter = painterResource(R.drawable.baseline_aspect_ratio_24), contentDescription = null)
            Text(text = "Surface: ${estate.size}")
        }
        Row {
            Icon(imageVector = Icons.Default.Home, contentDescription = null)
            Text(text = "Nombre de pièces: ${estate.numberOfRooms}")
        }
        Row {
            Icon(painter = painterResource(R.drawable.baseline_bed_24), contentDescription = null)
            Text(text = "Nombre de chambres: ${estate.numberOfBedrooms}")
        }
        Row {
            Icon(painter = painterResource(R.drawable.baseline_bathtub_24), contentDescription = null)
            Text(text = "Nombre de salles de bains: ${estate.numberOfBathrooms}")
        }
        Row {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
            Text(text = "Localisation: ${estate.address}")
        }


        // You can add a Map composable here for the location with a pin on the address
    }
}

@Preview
@Composable
fun EstateTest(){
    val estate = Estate("House","$100,000","300m2",5,3,1,"Ce petit texte décrit le bien immobilier","https://images.pexels.com/photos/323780/pexels-photo-323780.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1","New York","","","","","")
    EstateDetailsScreen(estate = estate)
}