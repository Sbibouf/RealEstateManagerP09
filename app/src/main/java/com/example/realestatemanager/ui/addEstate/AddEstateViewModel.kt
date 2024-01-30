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

    private val _photoList : MutableStateFlow<List<EstatePhoto>> = MutableStateFlow(emptyList())

    val photoList : StateFlow<List<EstatePhoto>> get() = _photoList


    fun updateEstate(transform : Estate.()->Estate){
        _estate.value = transform.invoke(_estate.value)
    }

    fun updatePhotoList(transform: List<EstatePhoto>.()-> List<EstatePhoto>){
        _photoList.value = transform.invoke(_photoList.value)
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
        latitude : String?,
        longitude : String?,
        soldState : Boolean?,
        entryDate : String?,
        soldDate : String?,
        agent : String?,
        school : Boolean?,
        shops : Boolean?,
        parc : Boolean?,
        hospital : Boolean?,
        restaurant : Boolean?,
        sport : Boolean?){

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
                    latitude,
                    longitude,
                    soldState,
                    entryDate,
                    soldDate,
                    agent,
                    school,
                    shops,
                    parc,
                    hospital,
                    restaurant,
                    sport
                )
            )
        }
    }

    fun insertHoleEstate(estate:Estate){
        executor.execute {
            estateRepository.insertEstate(
                Estate(
                    estate.type,
                    estate.price,
                    estate.size,
                    estate.numberOfRooms,
                    estate.numberOfBedrooms,
                    estate.numberOfBathrooms,
                    estate.description,
                    estate.address,
                    estate.city,
                    estate.latitude,
                    estate.longitude,
                    estate.soldState,
                    estate.entryDate,
                    estate.soldDate,
                    estate.agent,
                    estate.school,
                    estate.shops,
                    estate.parc,
                    estate.hospital,
                    estate.restaurant,
                    estate.sport
                )
            )
        }
    }
}