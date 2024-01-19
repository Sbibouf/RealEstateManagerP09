package com.example.realestatemanager.ui.addEstate

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.realestatemanager.data.local.repository.EstateRepository
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstatePhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject


@HiltViewModel
class AddEstateViewModel @Inject constructor(private val estateRepository: EstateRepository, private val executor: Executor = Executors.newSingleThreadExecutor()) : ViewModel() {

    fun insertEstate(
        type : String?,
        price : String?,
        size : String?,
        numberOfRooms : String?,
        numberOfBedrooms : String?,
        numberOfBathrooms : String?,
        description : String?,
        picture : List<EstatePhoto>?,
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
                    picture,
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