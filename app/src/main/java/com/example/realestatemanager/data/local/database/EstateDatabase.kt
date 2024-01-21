package com.example.realestatemanager.data.local.database

import androidx.room.RoomDatabase
import com.example.realestatemanager.data.local.dao.LibraryDao
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstatePhoto


@androidx.room.Database(entities = [Estate::class, EstatePhoto::class], version = 1, exportSchema = false)
abstract class EstateDatabase : RoomDatabase() {

    abstract fun libraryDao() : LibraryDao


}
