package com.example.realestatemanager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlintest.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEstate(){
    Scaffold(
        topBar = {
            TopAppBar( colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Gray,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White
            ),
                title = {
                    Text(text = "Real Estate Manager", style = TextStyle.Default.copy(fontSize = 16.sp))
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
                CreateEstate()

            }
        })
    }

@Composable
fun CreateEstate(){

        Column() {
            TextField(
                value = "",
                onValueChange ={},
                placeholder = {
                    Text(stringResource(R.string.Nom_du_bien_immobilier))},
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Home, contentDescription = null )
                },
                modifier = Modifier.fillMaxWidth())
            TextField(value = "address", onValueChange ={} )
            TextField(value = "Superficie", onValueChange ={} )
            TextField(value = "description", onValueChange ={} )
            TextField(value = "Price", onValueChange ={} )
            TextField(value = "Photos", onValueChange ={} )
            TextField(value = "Number of rooms", onValueChange ={} )
            TextField(value = "Number of bathrooms", onValueChange ={} )
            TextField(value = "Number of bedrooms", onValueChange ={} )
            TextField(value = "Location", onValueChange ={} )
            TextField(value = "Date d'entré", onValueChange ={} )
            TextField(value = "Agent en charge", onValueChange ={} )
            TextField(value = "Date de vente", onValueChange ={} )
        }


}

@Preview
@Composable
fun Preview(){
    AddEstate()
}
