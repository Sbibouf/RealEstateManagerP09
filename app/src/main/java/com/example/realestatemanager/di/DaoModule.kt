package com.example.realestatemanager.di

import android.content.Context

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.realestatemanager.data.local.dao.LibraryDao
import com.example.realestatemanager.data.local.database.EstateDatabase
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstatePhoto
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {
    lateinit var database: EstateDatabase
    @Provides
    fun provideContactDao(
        @ApplicationContext context: Context
    ): LibraryDao {
        /**
         * Create and return an instance of EstateDao
         */
        database = Room.databaseBuilder(
            context,
            EstateDatabase::class.java,
            "EstateDatabase.db"
        )
            /**
             * Prepopulate database with some estate
             */
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val libraryDao = database.libraryDao()
                    val executor = database.transactionExecutor
                    executor.execute {
                        libraryDao.insert(Estate("House", "$25.000.000", "225M²", "5","2","1", "","", "Manhattan", "", "", "", "",""))
                        libraryDao.insert(Estate("Penthouse", "$20.000.000", "225M²", "5","2","1", "","", "Brooklyn", "", "", "", "",""))
                        libraryDao.insert(Estate("House", "$15.000.000", "225M²", "5","2","1", "","", "Southampton", "", "", "", "",""))
                        libraryDao.insert(Estate("House", "$17.000.000", "225M²", "5","2","1", "","", "Upper East Side", "", "", "", "",""))
                        libraryDao.insert(Estate("House", "$20.000.000", "225M²", "5","2","1", "","", "Hampton Bays", "", "", "", "",""))
                        libraryDao.insert(Estate("House", "$22.000.000", "225M²", "5","2","1", "","", "Brooklyn", "", "", "", "",""))
                        libraryDao.insert(Estate("House", "$35.000.000", "250M²", "5","2","1", "","", "Montauk", "", "", "", "",""))

                        libraryDao.insertPhoto(EstatePhoto(1L,"/storage/emulated/0/Download/estate1_front.jpg", "Façade"))
                        libraryDao.insertPhoto(EstatePhoto(1L,"/storage/emulated/0/Download/estate1_living.jpg","Salon"))
                        libraryDao.insertPhoto(EstatePhoto(2L,"/storage/emulated/0/Download/estate2_front.jpg","Façade"))
                        libraryDao.insertPhoto(EstatePhoto(2L,"/storage/emulated/0/Download/estate2_living.jpg","Salon"))
                        libraryDao.insertPhoto(EstatePhoto(3L,"/storage/emulated/0/Download/estate3_front.jpg", "Façade"))
                        libraryDao.insertPhoto(EstatePhoto(3L,"/storage/emulated/0/Download/estate3_living.jpg","Salon"))
                        libraryDao.insertPhoto(EstatePhoto(4L,"/storage/emulated/0/Download/estate4_front.jpg", "Façade"))
                        libraryDao.insertPhoto(EstatePhoto(4L,"/storage/emulated/0/Download/estate4_kitchen.jpg","Cuisine"))
                        libraryDao.insertPhoto(EstatePhoto(5L,"/storage/emulated/0/Download/estate5_front.jpg", "Façade"))
                        libraryDao.insertPhoto(EstatePhoto(5L,"/storage/emulated/0/Download/estate5_kitchen.jpg","Cuisine"))
                        libraryDao.insertPhoto(EstatePhoto(6L,"/storage/emulated/0/Download/estate6_front.jpg", "Façade"))
                        libraryDao.insertPhoto(EstatePhoto(6L,"/storage/emulated/0/Download/estate6_living.jpg","Salon"))
                        libraryDao.insertPhoto(EstatePhoto(7L,"/storage/emulated/0/Download/estate7_front.jpg", "Façade"))
                        libraryDao.insertPhoto(EstatePhoto(7L,"/storage/emulated/0/Download/estate2_living.jpg","Salon"))
                    }

                }
            })
            .build()
        return database.libraryDao()
    }
    @Provides
    fun provideExecutor(): Executor{
        return Executors.newSingleThreadExecutor()
    }
}