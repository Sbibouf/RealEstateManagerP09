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
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {
    @Provides
    fun provideContactDao(
        @ApplicationContext context: Context
    ): EstateDao {
        /**
         * Create and return an instance of EstateDao
         */
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
    @Provides
    fun provideExecutor(): Executor{
        return Executors.newSingleThreadExecutor()
    }

    /**
     * Prepopulate the database with some estates
     */
    private fun prepopulateDatabase(database: EstateDatabase) {
        val estateDao = database.estateDao()
        if(!estateDao.isDatabasePrepopulate()) {
            estateDao.createEstate(Estate("House", "$25.000.000", "225M²", 5,2,1, "", "/storage/emulated/0/Download/estate1_front.jpg", "", "", "", "", "", ""))
            estateDao.createEstate(Estate("Penthouse", "$15.000.000", "190M²", 5,2,1, "", "/storage/emulated/0/Download/estate2_front.jpg", "", "", "", "", "", ""))
            estateDao.createEstate(Estate("House", "$20.000.000", "200M²", 5,2,1, "", "/storage/emulated/0/Download/estate3_front.jpg", "", "", "", "", "", ""))
            estateDao.createEstate(Estate("House", "$25.500.000", "230M²", 5,2,1, "", "/storage/emulated/0/Download/estate4_front.jpg", "", "", "", "", "", ""))
            estateDao.createEstate(Estate("House", "$12.000.000", "180M²", 5,2,1, "", "/storage/emulated/0/Download/estate5_front.jpg", "", "", "", "", "", ""))
            estateDao.createEstate(Estate("House", "$10.000.000", "150M²", 5,2,1, "", "/storage/emulated/0/Download/estate6_front.jpg", "", "", "", "", "", ""))
            estateDao.createEstate(Estate("House", "$27.000.000", "255M²", 5,2,1, "", "/storage/emulated/0/Download/estate7_front.jpg", "", "", "", "", "", ""))
            estateDao.markDatabasePrepopulated()
        }
    }
}