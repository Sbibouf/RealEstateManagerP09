package com.example.realestatemanager.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.realestatemanager.data.local.dao.EstateDao
import com.example.realestatemanager.data.local.database.EstateDatabase
import com.example.realestatemanager.model.Estate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {
    @Volatile
    private var INSTANCE: EstateDatabase? = null
    @Provides
    fun provideContactDao(
        @ApplicationContext context: Context
    ): EstateDao {
        // Create and return an instance of EstateDao
        val db = Room.databaseBuilder(
            context,
            EstateDatabase::class.java,
            "EstateDatabase.db"
        ).build()
        CoroutineScope(Dispatchers.IO).launch {
            prepopulateDatabase(db)
        }
        return db.estateDao()
    }

    private fun prepopulateDatabase(database: EstateDatabase) {
        val estateDao = database.estateDao()
        estateDao.createEstate(Estate(1L, "test", "", "", 5, "", "https://images.pexels.com/photos/53610/large-home-residential-house-architecture-53610.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "", "", "", "", "", ""))
    }
}