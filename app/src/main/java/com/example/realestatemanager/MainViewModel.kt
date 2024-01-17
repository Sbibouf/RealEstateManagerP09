package com.example.realestatemanager

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanager.data.local.repository.EstateRepository
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstatePhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val estateRepository: EstateRepository, private val executor: Executor = Executors.newSingleThreadExecutor()) : ViewModel() {

val uiState : StateFlow<List<Estate>> = estateRepository.getAllEstates().stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = emptyList())
    fun insertEstate( id : Long,
                  type : String,
                  price : String,
                  size : String,
                  numberOfRooms : Int,
                      numberOfBedrooms : Int,
                      numberOfBathrooms : Int,
                  description : String,
                  picture : List<EstatePhoto>,
                  address : String,
                      city : String,
                  placesOfInterest : String?,
                  state : String,
                  entryDate : String,
                  soldDate : String?,
                  agent : String){

    executor.execute{
        estateRepository.insertEstate(Estate(type,price,size,numberOfRooms, numberOfBedrooms, numberOfBathrooms, description, picture, address,city, placesOfInterest, state, entryDate, soldDate, agent))
    }
}
    //fun getAllEstate() : StateFlow<List<Estate>>{
      //  return estateRepository.getAllEstates()
    //}

}