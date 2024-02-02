package com.example.realestatemanager.model

data class SearchCriteria(
    val minPrice : String,
    val maxPrice : String,
    val minSize : String,
    val maxSize : String,
    val school : Boolean,
    val shops : Boolean,
    val parc : Boolean,
    val hospital : Boolean,
    val restaurant : Boolean,
    val sport : Boolean,
    val entryDate : String,
    val soldDate : String,
    val soldState : Boolean,

)