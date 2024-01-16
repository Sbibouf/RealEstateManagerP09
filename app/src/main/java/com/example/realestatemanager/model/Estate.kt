package com.example.realestatemanager.model


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Estate")
data class Estate(

    var type : String,
    var price : String,
    var size : String,
    var numberOfRooms : Int,
    var numberOfBedrooms : Int,
    var numberOfBathrooms : Int,
    var description : String,
    var picture : String,
    var address : String,
    var placesOfInterest : String?,
    var state : String,
    var entryDate : String,
    var soldDate : String?,
    var agent : String
    )  {
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
    var isPrepopulated : Boolean = false
}