package com.example.realestatemanager.model

data class SearchCriteria(
    var type : String = "",
    var minPrice : Int = 0,
    var maxPrice : Int = 0,
    var minSize : Int = 0,
    var maxSize : Int = 0,
    var numberOfRooms : Int = 0,
    var numberOfBedRooms : Int = 0,
    var numberOfBathRooms : Int = 0,
    var school : Boolean = false,
    var shops : Boolean = false,
    var parc : Boolean = false,
    var hospital : Boolean = false,
    var restaurant : Boolean = false,
    var sport : Boolean = false,
    var entryDate : String = "",
    var soldDate : String = "",
    var soldState : Boolean = false,

)