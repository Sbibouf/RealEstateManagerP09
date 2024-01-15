package com.example.realestatemanager

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.kotlinTest.EstateItem
import com.example.realestatemanager.model.Estate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEstate(estate : Estate){
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
        }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            //items(estate) { estate ->
                //EstateItem(estate, onDeleteClick = { }, onAddClick = { })
            }
        }
    }
