package com.example.realestatemanager.ui.addEstate

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.realestatemanager.data.local.repository.EstateRepository
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstatePhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject


@HiltViewModel
class AddEstateViewModel @Inject constructor(private val estateRepository: EstateRepository, private val executor: Executor = Executors.newSingleThreadExecutor()) : ViewModel() {

    private val _estate : MutableStateFlow<Estate> = MutableStateFlow(Estate())
     val estate : StateFlow<Estate> get() =  _estate


    fun updateEstate(transform : Estate.()->Estate){
        _estate.value = transform.invoke(_estate.value)
    }
    fun insertEstate(
        type : String?,
        price : String?,
        size : String?,
        numberOfRooms : String?,
        numberOfBedrooms : String?,
        numberOfBathrooms : String?,
        description : String?,
        address : String?,
        city : String?,
        placesOfInterest : String?,
        state : String?,
        entryDate : String?,
        soldDate : String?,
        agent : String?){

        Log.d("AddEstateViewModel", "Inserting estate: Type=$type, Price=$price, ...")
        executor.execute {
            estateRepository.insertEstate(
                Estate(
                    type,
                    price,
                    size,
                    numberOfRooms,
                    numberOfBedrooms,
                    numberOfBathrooms,
                    description,
                    address,
                    city,
                    placesOfInterest,
                    state,
                    entryDate,
                    soldDate,
                    agent
                )
            )
        }
    }
}