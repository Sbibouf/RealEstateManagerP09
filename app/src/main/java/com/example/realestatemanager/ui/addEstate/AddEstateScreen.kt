package com.example.realestatemanager.ui.addEstate

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.example.realestatemanager.R
import com.example.realestatemanager.data.local.service.MaskVisualTransformation
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstatePhoto
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.reflect.KFunction1
import kotlin.reflect.KFunction2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEstate(
    onAddEstateClick: (Estate, List<EstatePhoto>) -> Unit,
    estate: Estate,
    onUpdateEstate: KFunction1<Estate.() -> Estate, Unit>,
    photoList: List<EstatePhoto>,
    onBackClick: () -> Unit,
    onAddPhotoButtonClick: (EstatePhoto) -> Unit,
    onDeletePhotoClick: (EstatePhoto) -> Unit,
    onChangePhotoButtonClick: KFunction2<Uri, EstatePhoto, Unit>
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
                title = {
                    Text(
                        text = "Ajouter un bien immobilier",
                        style = TextStyle.Default.copy(fontSize = 16.sp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },

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
                    onAddEstateClick = onAddEstateClick,
                    estate = estate,
                    onUpdateEstate = onUpdateEstate,
                    photoList = photoList,
                    onAddPhotoButtonClick = onAddPhotoButtonClick,
                    onChangePhotoButtonClick = onChangePhotoButtonClick,
                    onDeletePhotoClick = onDeletePhotoClick
                )

            }
        })
}

@Composable
fun CreateEstate(
    onAddEstateClick: (Estate, List<EstatePhoto>) -> Unit,
    estate: Estate,
    onUpdateEstate: KFunction1<Estate.() -> Estate, Unit>,
    photoList: List<EstatePhoto>,
    onAddPhotoButtonClick: (EstatePhoto) -> Unit,
    onDeletePhotoClick: (EstatePhoto) -> Unit,
    onChangePhotoButtonClick: KFunction2<Uri, EstatePhoto, Unit>
) {

    //Variables and authorisation permission

    val context = LocalContext.current

    val estateId = remember { estate.id }


    var hasImage by remember {
        mutableStateOf(false)
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var imageName by remember { mutableStateOf("") }

    // Create an URI tempFile for the photo
    val imageUriProvider = remember {
        object {
            fun provideUri(): Uri {
                val tempFile = File.createTempFile(
                    "photo_", ".jpg", Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                    )
                )
                return FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    tempFile
                )
            }
        }
    }

    // If permission is granted, select photo from gallery and get a persistent access to it then add it to list
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedUri = data?.data
            hasImage = selectedUri != null
            imageUri = selectedUri
            val contentResolver = context.contentResolver
            contentResolver.takePersistableUriPermission(
                imageUri!!,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            onAddPhotoButtonClick(EstatePhoto(estate.id, imageUri.toString(), ""))
        }
    }

    //If permission is granted only get the photo from camera and add it if success
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
            if (success) {
                onAddPhotoButtonClick(EstatePhoto(estate.id, imageUri.toString(), ""))
            } else {
                hasImage = true
            }
        }
    )

    // Ask user for permission to access camera
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            val uri = imageUriProvider.provideUri()
            imageUri = uri
            cameraLauncher.launch(imageUri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    // Ask user for permission to access gallery
    val mediaPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            imagePicker.launch(Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "image/*"
            })
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    val estateType = listOf("House", "Flat", "Penthouse", "Villa", "Duplex", "Triplex")

    var dateType by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }


    //Composable

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier
            .padding(8.dp)
            .clickable { isDropDownMenuExpanded = true }) {


            estate.type?.let {
                OutlinedTextField(
                    value = it,
                    onValueChange = { newTextValue ->
                        onUpdateEstate { copy(type = newTextValue) }

                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    },
                    placeholder = {
                        Text(stringResource(R.string.Nom_du_bien_immobilier))
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(1.dp)
                        .clickable { isDropDownMenuExpanded = true },
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        disabledBorderColor = Color.Black,
                    ),
                    label = { Text(stringResource(R.string.Nom_du_bien_immobilier)) }

                )
            }
            DropdownMenu(
                expanded = isDropDownMenuExpanded,
                onDismissRequest = { isDropDownMenuExpanded = false }) {
                estateType.forEach { option ->
                    DropdownMenuItem(onClick = {
                        onUpdateEstate { copy(type = option) }
                        isDropDownMenuExpanded = false
                    }, text = { Text(text = option) })


                }
            }
            estate.size?.let {
                OutlinedTextField(
                    value = it,
                    onValueChange = { newTextValue ->
                        onUpdateEstate { copy(size = newTextValue) }
                    },
                    placeholder = {
                        Text(stringResource(R.string.Superficie))
                    },
                    label = { Text(stringResource(R.string.Superficie)) },
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
            OutlinedTextField(
                value = it,
                onValueChange = { newTextValue ->
                    onUpdateEstate { copy(address = newTextValue) }
                },
                placeholder = {
                    Text(stringResource(R.string.Address))
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                label = { Text(stringResource(R.string.Address)) }
            )
        }
        estate.description?.let {
            OutlinedTextField(
                value = it,
                onValueChange = { newTextValue ->
                    onUpdateEstate { copy(description = newTextValue) }
                },
                placeholder = {
                    Text(stringResource(R.string.Description))
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                label = { Text(stringResource(R.string.Description)) }
            )
        }
        estate.price?.let {
            OutlinedTextField(
                value = it,
                onValueChange = { newTextValue ->
                    if (it.length <= PriceDefaults.HIGH_PRICE_LENGTH) {
                        onUpdateEstate { copy(price = newTextValue.filter { char -> char.isDigit() }) }
                    }

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
                ),
                visualTransformation = getMask(it.length),
                label = { Text(stringResource(R.string.Price)) }
            )
        }

        Row {

            Text(
                text = stringResource(R.string.Photo),
                modifier = Modifier.padding(8.dp)
            )
            IconButton(
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val mediaPermissionCheckResult = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.READ_MEDIA_IMAGES
                        )
                        if (mediaPermissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            imagePicker.launch(Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                                type = "image/*"
                            })
                        } else {
                            mediaPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        }
                    } else {
                        val mediaPermissionCheckResult = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        if (mediaPermissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            imagePicker.launch(Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                                type = "image/*"
                            })
                        } else {
                            mediaPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    }


                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Default.AddCircle, contentDescription = null)
            }
            IconButton(onClick = {
                val uri = imageUriProvider.provideUri()

                val cameraPermissionCheckResult =
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                if (cameraPermissionCheckResult == PackageManager.PERMISSION_GRANTED) {

                    imageUri = uri
                    cameraLauncher.launch(imageUri)
                } else {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }


            }, modifier = Modifier.align(Alignment.CenterVertically)) {
                Icon(
                    painter = painterResource(R.drawable.baseline_photo_camera_24),
                    contentDescription = null
                )
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                    imageName = ""
                },
                title = {
                    Text("Donner un nom à l'image")
                },
                text = {
                    Column {
                        AsyncImage(
                            model = imageUri,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            contentDescription = "Selected image",
                        )

                        OutlinedTextField(
                            value = imageName,
                            onValueChange = { newName ->
                                imageName = newName
                            },
                            label = { Text("Nom de l'image") },
                            textStyle = TextStyle(color = Color.Black),
                            colors = OutlinedTextFieldDefaults.colors(
                                cursorColor = Color.Black
                            ),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done
                            ),
                            singleLine = true
                        )

                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            // Replace old photo by new one with name and reset imageName
                            onChangePhotoButtonClick(
                                imageUri!!,
                                EstatePhoto(estate.id, imageUri.toString(), imageName)
                            )
                            showDialog = false
                            imageName = ""

                        },
                        modifier = Modifier
                            .height(36.dp),
                    ) {
                        Text("Enregistrer")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDialog = false
                        },
                        modifier = Modifier
                            .height(36.dp),
                    ) {
                        Text("Annuler")
                    }
                },
            )
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            items(photoList) { photo ->
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp)
                        .clickable {
                            // Update imageUri with data and show dialog

                            imageUri = Uri.parse(photo.uri)
                            showDialog = true
                        },
                ) {
                    AsyncImage(
                        model = Uri.parse(photo.uri),
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp),
                        contentDescription = "Selected image",
                        contentScale = ContentScale.Crop
                    )
                    if (photo.name == "") {
                        Text(
                            text = "Nom Photo",
                            fontWeight = FontWeight.Bold,
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
                    } else {
                        photo.name?.let {
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
                            onDeletePhotoClick(photo)
                        }, modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            tint = Color.White,
                            contentDescription = null
                        )

                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }



        Row(modifier = Modifier.padding(8.dp)) {

            estate.numberOfRooms?.let {
                OutlinedTextField(
                    value = it,
                    onValueChange = { newTextValue ->
                        onUpdateEstate { copy(numberOfRooms = newTextValue) }
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
                        .padding(1.dp),
                    label = { Text(stringResource(R.string.Number_of_rooms)) }
                )
            }
            estate.numberOfBathrooms?.let {
                OutlinedTextField(
                    value = it,
                    onValueChange = { newTextValue ->
                        onUpdateEstate { copy(numberOfBathrooms = newTextValue) }
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
                        .padding(1.dp),
                    label = { Text(stringResource(R.string.Number_of_bathrooms)) }
                )
            }
            estate.numberOfBedrooms?.let {
                OutlinedTextField(
                    value = it,
                    onValueChange = { newTextValue ->
                        onUpdateEstate { copy(numberOfBedrooms = newTextValue) }
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
                        .padding(1.dp),
                    label = { Text(stringResource(R.string.Number_of_bedrooms)) }
                )
            }
        }

        Row(modifier = Modifier.padding(8.dp)) {

            estate.entryDate?.let {
                OutlinedTextField(
                    value = it,
                    onValueChange = { newValue ->
                        onUpdateEstate { copy(entryDate = newValue) }
                        //estate.entryDateMilli = convertDateToMillis(newValue)
                    },
                    placeholder = {
                        Text(stringResource(R.string.Date_entrée))
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(1.dp),
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        disabledBorderColor = Color.Black,
                    ),
                    label = { Text(stringResource(R.string.Date_entrée)) }
                )
            }
            IconButton(
                onClick = {
                    showDatePicker = true
                    dateType = "entryDate"
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
            }
            estate.soldDate?.let {
                OutlinedTextField(
                    value = it,
                    onValueChange = {
                        onUpdateEstate { copy(soldDate = it, soldState = true) }
                    },
                    placeholder = {
                        Text(stringResource(R.string.Date_vente))
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(1.dp),
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        disabledBorderColor = Color.Black,
                    ),
                    label = { Text(stringResource(R.string.Date_vente)) }
                )
            }
            IconButton(
                onClick = {
                    showDatePicker = true
                    dateType = "soldDate"
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
            }

            if (showDatePicker && dateType == "entryDate") {
                MyDatePickerDialog(
                    onDateSelected = {
                        onUpdateEstate {
                            copy(
                                entryDate = it,
                                entryDateMilli = convertDateToMillis(it)
                            )
                        }
                    },
                    onDismiss = { showDatePicker = false })

            } else if (showDatePicker && dateType == "soldDate") {
                MyDatePickerDialog(
                    onDateSelected = { onUpdateEstate { copy(soldDate = it, soldState = true) } },
                    onDismiss = { showDatePicker = false })
            }
        }


        estate.agent?.let {
            OutlinedTextField(
                value = it,
                onValueChange = { newTextValue ->
                    onUpdateEstate { copy(agent = newTextValue) }
                },

                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                label = { Text(stringResource(R.string.Agent)) }
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
                            onUpdateEstate { copy(school = true) }
                        } else {
                            onUpdateEstate { copy(school = false) }
                        }
                    },
                    enabled = true
                )
            }

            Text(text = "Commerce")
            estate.shops?.let {
                RadioButton(selected = it, onClick = {
                    if (estate.shops == false) {
                        onUpdateEstate { copy(shops = true) }
                    } else {
                        onUpdateEstate { copy(shops = false) }
                    }
                })
            }

            Text(text = "Parc")
            estate.parc?.let {
                RadioButton(selected = it, onClick = {
                    if (estate.parc == false) {
                        onUpdateEstate { copy(parc = true) }
                    } else {
                        onUpdateEstate { copy(parc = false) }
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
                            onUpdateEstate { copy(hospital = true) }
                        } else {
                            onUpdateEstate { copy(hospital = false) }
                        }
                    })
            }

            Text(text = "Restaurant")
            estate.restaurant?.let {
                RadioButton(
                    selected = it,
                    onClick = {
                        if (estate.restaurant == false) {
                            onUpdateEstate { copy(restaurant = true) }
                        } else {
                            onUpdateEstate { copy(restaurant = false) }
                        }
                    })
            }
            Text(text = "Sport")
            estate.sport?.let {
                RadioButton(selected = it, onClick = {
                    if (estate.sport == false) {
                        onUpdateEstate { copy(sport = true) }
                    } else {
                        onUpdateEstate { copy(sport = false) }
                    }
                })
            }
        }
        Button(onClick = {
            // If type and price fields are filled then we give back estateId and add it with its photo to database
            if (estate.type == "" || estate.price == "" || estate.entryDate == "") {
                Toast.makeText(
                    context,
                    "Veuillez renseigner un type, un prix et une date d'arrivée",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                estate.id = estateId
                onAddEstateClick(estate, photoList)
            }
        }) {
            if (estate.id == 0L) {
                Text(stringResource(R.string.Valider))
            } else {
                Text(stringResource(R.string.Modifier))
            }

        }
    }
}


private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
    return formatter.format(Date(millis))
}

private fun convertDateToMillis(dateString: String): Long {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
    val date = sdf.parse(dateString)
    return date?.time ?: 1
}

private fun getMask(length: Int): MaskVisualTransformation {
    return when (length) {
        7 -> MaskVisualTransformation(PriceDefaults.LOW_PRICE_MASK)
        8 -> MaskVisualTransformation(PriceDefaults.MID_PRICE_MASK)
        9 -> MaskVisualTransformation(PriceDefaults.HIGH_PRICE_MASK)
        else -> MaskVisualTransformation(PriceDefaults.HIGH_PRICE_MASK)
    }

}

object PriceDefaults {
    const val HIGH_PRICE_MASK = "$###.###.###.###"
    const val MID_PRICE_MASK = "$##.###.###"
    const val LOW_PRICE_MASK = "$#.###.###"
    const val HIGH_PRICE_LENGTH = 11
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= System.currentTimeMillis()
        }
    })

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate)
                onDismiss()
            }

            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "Annuler")
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

@Preview
@Composable
fun Preview() {
    // AddEstate(onButtonClick = {}, estate = Estate(), onUpdateEstate = {}, photoList = emptyList())
}
