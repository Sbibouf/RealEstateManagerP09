package com.example.realestatemanager.data.local.database

import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.realestatemanager.data.local.dao.EstateDao
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstateTypeConverters
import java.util.concurrent.Executors

@TypeConverters(EstateTypeConverters::class)
@androidx.room.Database(entities = [Estate::class], version = 1, exportSchema = false)
abstract class EstateDatabase : RoomDatabase() {

    abstract fun estateDao() : EstateDao

   // abstract val estateDao : EstateDao



}
