package com.example.realestatemanager.ui.addEstate


import android.Manifest
import android.annotation.SuppressLint
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
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
import coil.compose.AsyncImage
import com.example.realestatemanager.R
import com.example.realestatemanager.data.local.service.ComposeFileProvider
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstatePhoto


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEstate(onButtonClick: (Estate) -> Unit, onPhotoClick: () -> Unit) {
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
                CreateEstate(onButtonClick = onButtonClick, onPhotoClick = onPhotoClick)

            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun CreateEstate(onButtonClick: (Estate) -> Unit, onPhotoClick: () -> Unit) {
    var nomDuBien by remember { mutableStateOf("") }
    var superficie by remember { mutableStateOf("") }
    var adresse by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var prix by remember { mutableStateOf("") }
    var photos by remember { mutableStateOf("") }
    var nombreDePieces by remember { mutableStateOf("") }
    var nombreDeSalleDeau by remember { mutableStateOf("") }
    var nombreDeChambres by remember { mutableStateOf("") }
    var dateDentre by remember { mutableStateOf("") }
    var dateDeVente by remember { mutableStateOf("") }
    var agent by remember { mutableStateOf("") }

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

    var uri: Uri?

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            uri = ComposeFileProvider.getImageUri(context)
            imageUri = uri
            imagesListUri.add(uri)
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
    var imageName by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val imagesNameList = remember { mutableStateListOf<String?>() }
    val test by remember { mutableStateOf<EstatePhoto?>(null) }
    val test2 = remember { mutableStateListOf<List<EstatePhoto>?>() }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(modifier = Modifier.padding(8.dp)) {

            TextField(
                value = nomDuBien,
                onValueChange = { newTextValue ->
                    nomDuBien = newTextValue
                },
                placeholder = {
                    Text(stringResource(R.string.Nom_du_bien_immobilier))
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(1.dp)
            )
            TextField(
                value = superficie,
                onValueChange = { newTextValue ->
                    superficie = newTextValue
                },
                placeholder = {
                    Text(stringResource(R.string.Superficie))
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(1.dp)
            )

        }

        TextField(
            value = adresse,
            onValueChange = { newTextValue ->
                adresse = newTextValue
            },
            placeholder = {
                Text(stringResource(R.string.Address))
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
        TextField(
            value = description,
            onValueChange = { newTextValue ->
                description = newTextValue
            },
            placeholder = {
                Text(stringResource(R.string.Description))
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
        TextField(
            value = prix,
            onValueChange = { newTextValue ->
                prix = newTextValue
            },
            placeholder = {
                Text(stringResource(R.string.Price))
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )

        Row {

            TextField(
                value = photos,
                onValueChange = { newTextValue ->
                    photos = newTextValue
                },
                placeholder = {
                    Text(stringResource(R.string.Photo))
                },
                modifier = Modifier.padding(8.dp)
            )
            IconButton(
                onClick = {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val mediaPermissionCheckResult = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES)
                        if(mediaPermissionCheckResult == PackageManager.PERMISSION_GRANTED){
                            imagePicker.launch("image/*")
                        }
                        else {
                            //Request a permission
                            mediaPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        }
                    }
                    else {
                        val mediaPermissionCheckResult = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                        if(mediaPermissionCheckResult == PackageManager.PERMISSION_GRANTED){
                            imagePicker.launch("image/*")
                        }
                        else {
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
                uri = ComposeFileProvider.getImageUri(context)

                val cameraPermissionCheckResult =
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                if (cameraPermissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                    imageUri = uri
                    imagesListUri.add(uri)
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
                        selectedImageUri = null
                        imageName = ""
                    },
                    title = {
                        Text("Donner un nom à l'image")
                    },
                    text = {
                        Column {
                            AsyncImage(
                                model = selectedImageUri,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                contentDescription = "Selected image",
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                                value = imageName,
                                onValueChange = { newName ->
                                    imageName = newName
                                },
                                label = { Text("Nom de l'image") },
                                textStyle = TextStyle(color = Color.Black),
                                colors = OutlinedTextFieldDefaults.colors(
                                    cursorColor = Color.Black
                                )
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                // Fermez le dialogue
                                imagesNameList.add(imageName)
                                showDialog = false
                                // Réinitialisez l'image sélectionnée et le nom
                                selectedImageUri = null
                                imageName = ""

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
                                selectedImageUri = null
                            }
                        ) {
                            Text("Annuler")
                        }
                    }
                )
            }

            LazyRow(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(imagesListUri) { index, uri ->
                    Box(
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp)
                            .clickable {
                                // Mettre à jour l'image sélectionnée lorsque l'utilisateur clique
                                selectedImageUri = uri
                                // Afficher le dialogue pour donner un nom à l'image
                                showDialog = true
                            },
                    ) {
                        AsyncImage(
                            model = uri,
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp),
                            contentDescription = "Selected image",
                        )
                        if (imagesNameList.size > index) {


                            imagesNameList[index]?.let {
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


                        }
                        IconButton(
                            onClick = {
                                imagesListUri.remove(uri)
                                imagesNameList.removeAt(index)
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

            TextField(
                value = nombreDePieces,
                onValueChange = {
                    nombreDePieces = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = {
                    Text(stringResource(R.string.Number_of_rooms))
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(1.dp)
            )
            TextField(
                value = nombreDeSalleDeau,
                onValueChange = {
                    nombreDeSalleDeau = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = {
                    Text(stringResource(R.string.Number_of_bathrooms))
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(1.dp)
            )
            TextField(
                value = nombreDeChambres,
                onValueChange = {
                    nombreDeChambres = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = {
                    Text(stringResource(R.string.Number_of_bedrooms))
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(1.dp)
            )
        }

        Row(modifier = Modifier.padding(8.dp)) {

            TextField(
                value = dateDentre,
                onValueChange = { newTextValue ->
                    dateDentre = newTextValue
                },
                placeholder = {
                    Text(stringResource(R.string.Date_entrée))
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(1.dp)
            )
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
            }
            TextField(
                value = dateDeVente,
                onValueChange = { newTextValue ->
                    dateDeVente = newTextValue
                },
                placeholder = {
                    Text(stringResource(R.string.Date_vente))
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(1.dp)
            )
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
            }
        }

        TextField(
            value = agent,
            onValueChange = { newTextValue ->
                agent = newTextValue
            },
            placeholder = {
                Text(stringResource(R.string.Agent))
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )

        Button(onClick = {
            val data = Estate(
                type = nomDuBien,
                price = prix,
                size = superficie,
                numberOfRooms = nombreDePieces,
                numberOfBedrooms = nombreDeChambres,
                numberOfBathrooms = nombreDeSalleDeau,
                description = description,
                address = adresse,
                city = adresse,
                placesOfInterest = null,
                state = null,
                entryDate = null,
                soldDate = null,
                agent = agent
            )
            onButtonClick(data)
        }) {
            Text(stringResource(R.string.Valider))

        }

    }
}

@Preview
@Composable
fun Preview() {
    AddEstate(onButtonClick = {}, onPhotoClick = {})
}
