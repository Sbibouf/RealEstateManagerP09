package com.example.realestatemanager.ui.addEstate

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.realestatemanager.R
import com.example.realestatemanager.data.local.service.ComposeFileProvider
import com.example.realestatemanager.data.local.service.MaskVisualTransformation
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstatePhoto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEstate(onButtonClick: (Estate) -> Unit, estate: Estate, photoList: List<EstatePhoto?>) {
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
                    IconButton(onClick = { /* Handle navigation icon click */ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                    }
                },
                actions = {

                    IconButton(onClick = { /* Handle search icon click */ }) {
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
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                CreateEstate(
                    onButtonClick = onButtonClick,
                    estate = estate,
                    photoList = photoList
                )

            }
        })
}

@Composable
fun CreateEstate(
    onButtonClick: (Estate) -> Unit,
    estate: Estate,
    viewModel: AddEstateViewModel = viewModel(),
    photoList: List<EstatePhoto?>
) {
    var estatePhoto by remember { mutableStateOf(EstatePhoto()) }

    val context = LocalContext.current

    var hasImage by remember {
        mutableStateOf(false)
    }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val imagesListUri = remember { mutableStateListOf<Uri?>() }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
            imagesListUri.add(uri)

        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            val uri = ComposeFileProvider.getImageUri(context)
            viewModel.updatePhotoList { listOf(EstatePhoto(estate.id, uri.toString(), "")) }
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    val mediaPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            imagePicker.launch("image/*")
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }


    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Ajouter un bien immobilier", modifier = Modifier.padding(16.dp))

        Row(modifier = Modifier.padding(8.dp)) {


            estate.type?.let {
                TextField(
                    value = it,
                    onValueChange = { newTextValue ->
                        viewModel.updateEstate { copy(type = newTextValue) }

                    },
                    placeholder = {
                        Text(stringResource(R.string.Nom_du_bien_immobilier))
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(1.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
            }
            estate.size?.let {
                TextField(
                    value = it,
                    onValueChange = { newTextValue ->
                        viewModel.updateEstate { copy(size = newTextValue) }
                    },
                    placeholder = {
                        Text(stringResource(R.string.Superficie))
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(1.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
            }

        }

        estate.address?.let {
            TextField(
                value = it,
                onValueChange = { newTextValue ->
                    viewModel.updateEstate { copy(address = newTextValue) }
                },
                placeholder = {
                    Text(stringResource(R.string.Address))
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
        }
        estate.description?.let {
            TextField(
                value = it,
                onValueChange = { newTextValue ->
                    viewModel.updateEstate { copy(description = newTextValue) }
                },
                placeholder = {
                    Text(stringResource(R.string.Description))
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
        }
        estate.price?.let {
            TextField(
                value = it,
                onValueChange = { newTextValue ->
                    viewModel.updateEstate { copy(price = newTextValue) }
                },
                placeholder = {
                    Text(stringResource(R.string.Price))
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
        }

        Row {
            
            Text(text = stringResource(R.string.Photo),
                modifier = Modifier.padding(8.dp))
            IconButton(
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val mediaPermissionCheckResult = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.READ_MEDIA_IMAGES
                        )
                        if (mediaPermissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            imagePicker.launch("image/*")
                        } else {
                            //Request a permission
                            mediaPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        }
                    } else {
                        val mediaPermissionCheckResult = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        if (mediaPermissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            imagePicker.launch("image/*")
                        } else {
                            //Request a permission
                            mediaPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    }


                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Default.AddCircle, contentDescription = null)
            }
            IconButton(onClick = {
                val uri = ComposeFileProvider.getImageUri(context)

                val cameraPermissionCheckResult =
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                if (cameraPermissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                    imageUri = uri
                    imagesListUri.add(uri)
                    viewModel.updatePhotoList { listOf(EstatePhoto(estate.id, uri.toString(), "")) }
                    cameraLauncher.launch(uri)
                } else {
                    // Request a permission
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }


                // cameraLauncher.launch(uri)
            }, modifier = Modifier.align(Alignment.CenterVertically)) {
                Icon(
                    painter = painterResource(R.drawable.baseline_photo_camera_24),
                    contentDescription = null
                )
            }
        }

        if (hasImage && imageUri != null) {
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                        // Réinitialisez l'image sélectionnée et le nom
                        estatePhoto = EstatePhoto()
                    },
                    title = {
                        Text("Donner un nom à l'image")
                    },
                    text = {
                        Column {
                            AsyncImage(
                                model = Uri.parse(estatePhoto.uri),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                contentDescription = "Selected image",
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            estatePhoto.name?.let {
                                OutlinedTextField(
                                    value = it,
                                    onValueChange = { newName ->
                                        estatePhoto.name = newName
                                    },
                                    label = { Text("Nom de l'image") },
                                    textStyle = TextStyle(color = Color.Black),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        cursorColor = Color.Black
                                    )
                                )
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                // Fermez le dialogue
                                //viewModel.updatePhotoList { listOf(EstatePhoto(estate.id, estatePhoto.uri,estatePhoto.name)) }
                                showDialog = false
                                // Réinitialisez l'image sélectionnée et le nom
                                estatePhoto = EstatePhoto()

                            }
                        ) {
                            Text("Enregistrer")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {

                                showDialog = false
                                // Réinitialisez l'image sélectionnée et le nom
                                estatePhoto = EstatePhoto()
                            }
                        ) {
                            Text("Annuler")
                        }
                    }
                )
            }

            LazyRow(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(photoList) { index, photo ->
                    Box(
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp)
                            .clickable {
                                // Mettre à jour l'image sélectionnée lorsque l'utilisateur clique
                                if (photo != null) {
                                    estatePhoto = photo
                                }
                                // Afficher le dialogue pour donner un nom à l'image
                                showDialog = true
                            },
                    ) {
                        AsyncImage(
                            model = Uri.parse(photo?.uri),
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp),
                            contentDescription = "Selected image",
                        )

                        photo?.name?.let {
                            Text(
                                text = it,
                                color = Color.White,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .width(100.dp)
                                    .background(Color.Black.copy(alpha = 0.5f))
                                    .padding(4.dp)
                                    .align(Alignment.BottomCenter)
                            )
                        }

                        IconButton(
                            onClick = {

                            }, modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = null)

                        }
                    }
                }
            }

        }

        Row(modifier = Modifier.padding(8.dp)) {

            estate.numberOfRooms?.let {
                TextField(
                    value = it,
                    onValueChange = { newTextValue ->
                        viewModel.updateEstate { copy(numberOfRooms = newTextValue) }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    placeholder = {
                        Text(stringResource(R.string.Number_of_rooms))
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(1.dp)
                )
            }
            estate.numberOfBathrooms?.let {
                TextField(
                    value = it,
                    onValueChange = { newTextValue ->
                        viewModel.updateEstate { copy(numberOfBathrooms = newTextValue) }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    placeholder = {
                        Text(stringResource(R.string.Number_of_bathrooms))
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(1.dp)
                )
            }
            estate.numberOfBedrooms?.let {
                TextField(
                    value = it,
                    onValueChange = { newTextValue ->
                        viewModel.updateEstate { copy(numberOfBedrooms = newTextValue) }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    placeholder = {
                        Text(stringResource(R.string.Number_of_bedrooms))
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(1.dp)
                )
            }
        }

        Row(modifier = Modifier.padding(8.dp)) {

            estate.entryDate?.let {
                TextField(
                    value = it,
                    onValueChange = {
                        if (it.length <= DateDefaults.DATE_LENGTH) {
                            viewModel.updateEstate { copy(entryDate = it) }
                        }

                    },
                    placeholder = {
                        Text(stringResource(R.string.Date_entrée))
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(1.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = MaskVisualTransformation(DateDefaults.DATE_MASK)
                )
            }
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
            }
            estate.soldDate?.let {
                TextField(
                    value = it,
                    onValueChange = {
                        if (it.length <= DateDefaults.DATE_LENGTH) {
                            viewModel.updateEstate { copy(soldDate = it) }
                        }

                    },
                    placeholder = {
                        Text(stringResource(R.string.Date_vente))
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(1.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = MaskVisualTransformation(DateDefaults.DATE_MASK)
                )
            }
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
            }
        }

        estate.agent?.let {
            TextField(
                value = it,
                onValueChange = { newTextValue ->
                    viewModel.updateEstate { copy(agent = newTextValue) }
                },
                placeholder = {
                    Text(stringResource(R.string.Agent))
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
        }
        Text(text = "Points d'interet à proximité :", modifier = Modifier.padding(vertical = 8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Ecole")
            estate.school?.let {
                RadioButton(
                    selected = it,
                    onClick = {
                        if (estate.school == false) {
                            viewModel.updateEstate { copy(school = true) }
                        } else {
                            viewModel.updateEstate { copy(school = false) }
                        }
                    },
                    enabled = true
                )
            }

            Text(text = "Commerce")
            estate.shops?.let {
                RadioButton(selected = it, onClick = {
                    if (estate.shops == false) {
                        viewModel.updateEstate { copy(shops = true) }
                    } else {
                        viewModel.updateEstate { copy(shops = false) }
                    }
                })
            }

            Text(text = "Parc")
            estate.parc?.let {
                RadioButton(selected = it, onClick = {
                    if (estate.parc == false) {
                        viewModel.updateEstate { copy(parc = true) }
                    } else {
                        viewModel.updateEstate { copy(parc = false) }
                    }
                })
            }

        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Hopital")
            estate.hospital?.let {
                RadioButton(
                    selected = it,
                    onClick = {
                        if (estate.hospital == false) {
                            viewModel.updateEstate { copy(hospital = true) }
                        } else {
                            viewModel.updateEstate { copy(hospital = false) }
                        }
                    })
            }

            Text(text = "Restaurant")
            estate.restaurant?.let {
                RadioButton(
                    selected = it,
                    onClick = {
                        if (estate.restaurant == false) {
                            viewModel.updateEstate { copy(restaurant = true) }
                        } else {
                            viewModel.updateEstate { copy(restaurant = false) }
                        }
                    })
            }

            Text(text = "Sport")
            estate.sport?.let {
                RadioButton(selected = it, onClick = {
                    if (estate.sport == false) {
                        viewModel.updateEstate { copy(sport = true) }
                    } else {
                        viewModel.updateEstate { copy(sport = false) }
                    }
                })
            }
        }


        Button(onClick = {
            onButtonClick(estate)
        }) {
            Text(stringResource(R.string.Valider))

        }


    }
}

object DateDefaults {
    const val DATE_MASK = "##/##/####"
    const val DATE_LENGTH = 8 // Equals to "##/##/####".count { it == '#' }
}

@Preview
@Composable
fun Preview() {
    AddEstate(onButtonClick = {}, estate = Estate(), photoList = emptyList())
}
