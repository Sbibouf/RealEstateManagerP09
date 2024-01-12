package com.example.realestatemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlinTest.EstateList
import com.example.kotlinTest.GetEstateList
import com.example.realestatemanager.data.local.repository.EstateRepository
import com.example.kotlinTest.ui.theme.KotlinTestTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var estateRepository : EstateRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onAddClickTest()
        setContent {
            KotlinTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val viewModel: MainViewModel = viewModel()
                    EstateList()

                }
            }
        }


    }

    fun onAddClickTest(){
        estateRepository.getAllEstates().observe(this, Observer { newData ->
            GetEstateList(newData)
        })

    }


}



