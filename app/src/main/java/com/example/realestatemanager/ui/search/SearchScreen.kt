package com.example.realestatemanager.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.realestatemanager.R
import com.example.realestatemanager.model.SearchCriteria
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCriteria(searchCriteria: SearchCriteria, onBackClick: () -> Unit) {

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
                        text = "Recherche de bien immobilier",
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

                SearchForm(searchCriteria = searchCriteria)
            }

        })
}

@Composable
fun SearchForm(
    searchCriteria: SearchCriteria,
) {

    var showDatePicker by remember { mutableStateOf(false) }
    var dateType by remember { mutableStateOf("") }

    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    val estateType = listOf("House", "Flat", "Penthouse", "Villa", "Duplex", "Triplex")

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = searchCriteria.type,
                onValueChange = { newValue -> searchCriteria.type = newValue },
                placeholder = {
                    Text(stringResource(R.string.Nom_du_bien_immobilier))
                },
                enabled = false,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    disabledBorderColor = Color.Black,
                ),
                modifier = Modifier.clickable { isDropDownMenuExpanded = true })

            DropdownMenu(
                expanded = isDropDownMenuExpanded,
                onDismissRequest = { isDropDownMenuExpanded = false }) {
                estateType.forEach { option ->
                    DropdownMenuItem(onClick = {
                        searchCriteria.type = option
                        isDropDownMenuExpanded = false
                    }, text = { Text(text = option) })


                }
            }

            Spacer(modifier = Modifier.height(8.dp))


            Row {
                OutlinedTextField(
                    value = searchCriteria.minPrice.toString(),
                    onValueChange = { newValue ->
                        searchCriteria.minPrice = newValue.toInt()
                    },
                    modifier = Modifier.weight(1f),
                    label = { Text("Prix minimum") }
                )
                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = searchCriteria.maxPrice.toString(),
                    onValueChange = { newValue ->
                        searchCriteria.maxPrice = newValue.toInt()
                    },
                    modifier = Modifier.weight(1f),
                    label = { Text("Prix maximum") }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                OutlinedTextField(
                    value = searchCriteria.minSize.toString(),
                    onValueChange = { newValue ->
                        searchCriteria.minSize = newValue.toInt()
                    },
                    modifier = Modifier.weight(1f),
                    label = { Text("Surface minimum") }
                )
                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = searchCriteria.maxSize.toString(),
                    onValueChange = { newValue ->
                        searchCriteria.maxSize = newValue.toInt()
                    },
                    modifier = Modifier.weight(1f),
                    label = { Text("Surface maximum") }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row {
                OutlinedTextField(
                    value = searchCriteria.numberOfRooms.toString(),
                    onValueChange = { newValue -> searchCriteria.numberOfRooms = newValue.toInt() },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    placeholder = {
                        Text(stringResource(R.string.Number_of_rooms))
                    },
                    modifier = Modifier
                        .weight(1f),
                    label = { Text("Nombre de pièces") }
                )
                Spacer(modifier = Modifier.width(4.dp))

                OutlinedTextField(
                    value = searchCriteria.numberOfBedRooms.toString(),
                    onValueChange = { newValue ->
                        searchCriteria.numberOfBedRooms = newValue.toInt()
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    placeholder = {
                        Text(stringResource(R.string.Number_of_bedrooms))
                    },
                    modifier = Modifier
                        .weight(1f),
                    label = { Text("Nombre de chambres") }
                )

                Spacer(modifier = Modifier.width(4.dp))
                OutlinedTextField(
                    value = searchCriteria.numberOfBathRooms.toString(),
                    onValueChange = { newValue ->
                        searchCriteria.numberOfBathRooms = newValue.toInt()
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    placeholder = {
                        Text(stringResource(R.string.Number_of_bathrooms))
                    },
                    modifier = Modifier
                        .weight(1f),
                    label = { Text("Nombre de salle d'eau") }
                )
            }
            Text(
                text = "Points d'interets à proximité :",
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(text = "Ecole")
                RadioButton(selected = searchCriteria.school, onClick = { /*TODO*/ })
                Text(text = "Commerce")
                RadioButton(selected = searchCriteria.shops, onClick = { /*TODO*/ })
                Text(text = "Parc")
                RadioButton(selected = searchCriteria.parc, onClick = { /*TODO*/ })
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Hopital")
                RadioButton(selected = searchCriteria.hospital, onClick = { /*TODO*/ })
                Text(text = "Restaurant")
                RadioButton(selected = searchCriteria.restaurant, onClick = { /*TODO*/ })
                Text(text = "Sport")
                RadioButton(selected = searchCriteria.sport, onClick = { /*TODO*/ })
            }
            Row {
                OutlinedTextField(
                    value = searchCriteria.entryDate,
                    onValueChange = { newValue ->
                        searchCriteria.entryDate = newValue
                    },
                    modifier = Modifier.weight(1f),
                    placeholder = { stringResource(R.string.Date_entrée) },
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        disabledBorderColor = Color.Black,
                    ),
                    label = { Text("Date d'entrée") }
                )
                IconButton(onClick = {
                    showDatePicker = true
                    dateType = "entryDate"
                }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                }
                OutlinedTextField(
                    value = searchCriteria.soldDate,
                    onValueChange = { newValue ->
                        searchCriteria.soldDate = newValue
                    },
                    modifier = Modifier.weight(1f),
                    placeholder = { stringResource(R.string.Date_vente) },
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        disabledBorderColor = Color.Black,
                    ),
                    label = { Text("Date de vente") }
                )
                IconButton(onClick = {
                    showDatePicker = true
                    dateType = "soldDate"
                }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {

            }) {
                Text(stringResource(R.string.Rechercher))
            }
        }


    }
    if (showDatePicker && dateType == "entryDate") {
        MyDatePickerDialog(
            onDateSelected = { newValue -> searchCriteria.entryDate = newValue },
            onDismiss = { showDatePicker = false })

    } else if (showDatePicker && dateType == "soldDate") {
        MyDatePickerDialog(
            onDateSelected = { newValue -> searchCriteria.soldDate = newValue },
            onDismiss = { showDatePicker = false })
    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
    return formatter.format(Date(millis))
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
fun Search() {
    val searchCriteria = SearchCriteria()
    SearchCriteria(searchCriteria = searchCriteria) {

    }
}