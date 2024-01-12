package com.example.realestatemanager

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.realestatemanager.data.local.repository.EstateRepository
import com.example.realestatemanager.model.Estate

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

class MainViewModel @Inject constructor(private val estateRepository: EstateRepository, private val executor: Executor = Executors.newSingleThreadExecutor()) : ViewModel() {


    fun insertEstate( id : Long,
                  type : String,
                  price : String,
                  size : String,
                  numberOfRooms : Int,
                  description : String,
                  picture : String,
                  address : String,
                  placesOfInterest : String?,
                  state : String,
                  entryDate : String,
                  soldDate : String?,
                  agent : String){

    executor.execute{
        estateRepository.insertEstate(Estate(id,type,price,size,numberOfRooms, description, picture, address, placesOfInterest, state, entryDate, soldDate, agent))
    }
}
    fun getAllEstate() : LiveData<List<Estate>>{
        return estateRepository.getAllEstates()
    }

}