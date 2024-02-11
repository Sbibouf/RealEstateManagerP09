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
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RangeSlider
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
import kotlin.reflect.KFunction1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCriteria(searchCriteria: SearchCriteria, onBackClick: () -> Unit, onSearchClick : (SearchCriteria)->Unit, onUpdateSearch : KFunction1<SearchCriteria.() -> SearchCriteria, Unit>) {

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

                SearchForm(searchCriteria = searchCriteria, onSearchClick = onSearchClick, onUpdateSearch =onUpdateSearch)
            }

        })
}

@Composable
fun SearchForm(
    searchCriteria: SearchCriteria,
    onSearchClick: (SearchCriteria)->Unit,
    onUpdateSearch: KFunction1<SearchCriteria.() -> SearchCriteria, Unit>
) {

    var showDatePicker by remember { mutableStateOf(false) }
    var dateType by remember { mutableStateOf("") }
    var isRadioButtonSelected by remember { mutableStateOf(true) }

    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    val estateType = listOf("House", "Flat", "Penthouse", "Villa", "Duplex", "Triplex", "Tous les biens immobiliers")


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

            searchCriteria.type?.let {
                OutlinedTextField(
                    value = it,
                    onValueChange = { newValue -> onUpdateSearch{copy (type = newValue)} },
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
            }

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
                        onUpdateSearch{copy (minPrice = newValue.filter { char -> char.isDigit() }.takeIf { it.isNotEmpty() }?.toIntOrNull() ?: 0)}
                    },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    label = { Text("Prix minimum") }
                )
                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = searchCriteria.maxPrice.toString(),
                    onValueChange = { newValue ->
                        onUpdateSearch{copy (maxPrice = newValue.filter { char -> char.isDigit() }.takeIf { it.isNotEmpty() }?.toIntOrNull() ?: 0)}
                    },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    label = { Text("Prix maximum") }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                OutlinedTextField(
                    value = searchCriteria.minSize.toString(),
                    onValueChange = { newValue ->
                        onUpdateSearch{copy (minSize = newValue.filter { char -> char.isDigit() }.takeIf { it.isNotEmpty() }?.toIntOrNull() ?: 0)}
                    },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    label = { Text("Surface minimum") }
                )
                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = searchCriteria.maxSize.toString(),
                    onValueChange = { newValue ->
                        onUpdateSearch{copy (maxSize = newValue.filter { char -> char.isDigit() }.takeIf { it.isNotEmpty() }?.toIntOrNull() ?: 0)}
                    },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    label = { Text("Surface maximum") }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row {
                OutlinedTextField(
                    value = searchCriteria.minNumberOfRooms.toString(),
                    onValueChange = { newValue -> onUpdateSearch { copy(minNumberOfRooms = newValue.filter { char -> char.isDigit() }.takeIf { it.isNotEmpty() }?.toIntOrNull() ?: 0) } },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    placeholder = {
                        Text(stringResource(R.string.Number_of_rooms))
                    },
                    modifier = Modifier
                        .weight(1f),
                    label = { Text("Nombre de pièces minimum") }
                )
                Spacer(modifier = Modifier.width(4.dp))
                OutlinedTextField(
                    value = searchCriteria.maxNumberOfRooms.toString(),
                    onValueChange = { newValue -> onUpdateSearch { copy(maxNumberOfRooms = newValue.filter { char -> char.isDigit() }.takeIf { it.isNotEmpty() }?.toIntOrNull() ?: 0) } },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    placeholder = {
                        Text(stringResource(R.string.Number_of_rooms))
                    },
                    modifier = Modifier
                        .weight(1f),
                    label = { Text("Nombre de pièces maximum") }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {

                OutlinedTextField(
                    value = searchCriteria.minNumberOfBedrooms.toString(),
                    onValueChange = { newValue ->
                        onUpdateSearch { copy(minNumberOfBedrooms = newValue.filter { char -> char.isDigit() }.takeIf { it.isNotEmpty() }?.toIntOrNull() ?: 0) }
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
                    label = { Text("Nombre de chambres minimum") }
                )
                Spacer(modifier = Modifier.width(4.dp))
                OutlinedTextField(
                    value = searchCriteria.maxNumberOfBedrooms.toString(),
                    onValueChange = { newValue ->
                        onUpdateSearch { copy(maxNumberOfBedrooms = newValue.filter { char -> char.isDigit() }.takeIf { it.isNotEmpty() }?.toIntOrNull() ?: 0) }
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
                    label = { Text("Nombre de chambres maximum") }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                OutlinedTextField(
                    value = searchCriteria.minNumberOfBathrooms.toString(),
                    onValueChange = { newValue ->
                        onUpdateSearch { copy(minNumberOfBathrooms = newValue.filter { char -> char.isDigit() }.takeIf { it.isNotEmpty() }?.toIntOrNull() ?: 0) }
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
                    label = { Text("Nombre de salle d'eau minimum") }
                )

                Spacer(modifier = Modifier.width(4.dp))

                OutlinedTextField(
                    value = searchCriteria.maxNumberOfBathrooms.toString(),
                    onValueChange = { newValue ->
                        onUpdateSearch { copy(maxNumberOfBathrooms = newValue.filter { char -> char.isDigit() }.takeIf { it.isNotEmpty() }?.toIntOrNull() ?: 0) }
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
                    label = { Text("Nombre de salle d'eau maximum") }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Points d'interets à proximité :",
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(text = "Ecole")
                searchCriteria.school?.let { RadioButton(selected = it, onClick = { if (searchCriteria.school == false) {
                    onUpdateSearch { copy(school = true) }
                } else {
                    onUpdateSearch { copy(school = false) }
                } }) }
                Text(text = "Commerce")
                searchCriteria.shops?.let { RadioButton(selected = it, onClick = { if (searchCriteria.shops == false) {
                    onUpdateSearch { copy(shops = true) }
                } else {
                    onUpdateSearch { copy(shops = false) }
                } }) }
                Text(text = "Parc")
                searchCriteria.parc?.let { RadioButton(selected = it, onClick = { if (searchCriteria.parc == false) {
                    onUpdateSearch { copy(parc = true) }
                } else {
                    onUpdateSearch { copy(parc = false) }
                } }) }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Hopital")
                searchCriteria.hospital?.let { RadioButton(selected = it, onClick = { if (searchCriteria.hospital == false) {
                    onUpdateSearch { copy(hospital = true) }
                } else {
                    onUpdateSearch { copy(hospital = false) }
                } }) }
                Text(text = "Restaurant")
                searchCriteria.restaurant?.let { RadioButton(selected = it, onClick = { if (searchCriteria.restaurant == false) {
                    onUpdateSearch { copy(restaurant = true) }
                } else {
                    onUpdateSearch { copy(restaurant = false) }
                } }) }
                Text(text = "Sport")
                searchCriteria.sport?.let { RadioButton(selected = it, onClick = { if (searchCriteria.sport == false) {
                    onUpdateSearch { copy(sport = true) }
                } else {
                    onUpdateSearch { copy(sport = false) }
                } }) }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Mise sur le marché depuis : ")
            Row {
                searchCriteria.entryDate?.let {
                    OutlinedTextField(
                        value = it,
                        onValueChange = { newValue ->
                            onUpdateSearch{copy (entryDate = newValue)}
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
                        label = { Text("Date d'entrée sur le marché") }
                    )
                }
                IconButton(onClick = {
                    showDatePicker = true
                }, modifier = Modifier.align(Alignment.CenterVertically)) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                }

            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Toujours en vente ?")
                Spacer(modifier = Modifier.width(16.dp))
                searchCriteria.soldState?.let { RadioButton(selected = it, onClick = { onUpdateSearch{copy(soldState = true)}
                isRadioButtonSelected = false}) }
                Text(text = "oui")
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(selected = isRadioButtonSelected, onClick = { onUpdateSearch{copy(soldState = false)}
                isRadioButtonSelected = true})
                Text(text = "non")
            }

            Button(onClick = {
                onSearchClick(searchCriteria)

            }) {
                Text(stringResource(R.string.Rechercher))
            }
        }


    }
    if (showDatePicker) {
        MyDatePickerDialog(
            onDateSelected = { newValue -> onUpdateSearch{copy (entryDate = newValue, entryDateMilli = convertDateToMillis(newValue))} },
            onDismiss = { showDatePicker = false })

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
    //SearchCriteria(searchCriteria = searchCriteria, onSearchClick = {}, onBackClick = {}, onUpdateSearch = {})


}