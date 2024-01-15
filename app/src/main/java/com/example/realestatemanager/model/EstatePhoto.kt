package com.example.realestatemanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URI

//@Entity(tableName = "EstatePhoto")
data class EstatePhoto(//@PrimaryKey
                       var id : Long,
                       var uri : URI,
                       var name : String)