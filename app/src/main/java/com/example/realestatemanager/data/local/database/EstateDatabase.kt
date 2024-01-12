package com.example.realestatemanager.data.local.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.realestatemanager.data.local.dao.EstateDao
import com.example.realestatemanager.model.Estate
import java.util.concurrent.Executors

@androidx.room.Database(entities = [Estate::class], version = 1, exportSchema = false)
abstract class EstateDatabase : RoomDatabase() {

    abstract fun estateDao() : EstateDao

   // abstract val estateDao : EstateDao



}
private fun prepopulateDatabase(): RoomDatabase.Callback {
    //val estateDao : EstateDao
    return object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Executors.newSingleThreadExecutor().execute{
                //INSTANCE?.estateDao()?.createEstate(Estate(1L,"test","100000","",5,"","","","","","","",""))
            }
        }
    }

}