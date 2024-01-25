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
                        libraryDao.insert(Estate("House", "$25.000.000", "225M²", "5","2","1", "","City Hall, New York", "Manhattan", "", "", "", "",""))
                        libraryDao.insert(Estate("Penthouse", "$20.000.000", "225M²", "5","2","1", "","Brooklyn", "Brooklyn", "", "", "", "",""))
                        libraryDao.insert(Estate("House", "$15.000.000", "225M²", "5","2","1", "","Southampton", "Southampton", "", "", "", "21/11/2023",""))
                        libraryDao.insert(Estate("House", "$17.000.000", "225M²", "5","2","1", "","Upper East Side", "Upper East Side", "", "", "", "",""))
                        libraryDao.insert(Estate("House", "$20.000.000", "225M²", "5","2","1", "","Hampton Bays", "Hampton Bays", "", "", "", "22/12/2023",""))
                        libraryDao.insert(Estate("House", "$22.000.000", "225M²", "5","2","1", "","Brooklyn", "Brooklyn", "", "", "", "",""))
                        libraryDao.insert(Estate("House", "$35.000.000", "250M²", "5","2","1", "","Montauk", "Montauk", "", "", "", "",""))

                        libraryDao.insert(Estate("House", "$35.000.000", "250M²", "5","2","1", "","", "Montauk", "", "", "", "",""))


                        libraryDao.insertPhoto(EstatePhoto(1L,"https://images.pexels.com/photos/259593/pexels-photo-259593.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Façade"))
                        libraryDao.insertPhoto(EstatePhoto(1L,"https://images.pexels.com/photos/3701434/pexels-photo-3701434.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1","Salon"))
                        libraryDao.insertPhoto(EstatePhoto(2L,"https://images.pexels.com/photos/323780/pexels-photo-323780.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1","Façade"))
                        libraryDao.insertPhoto(EstatePhoto(2L,"https://images.pexels.com/photos/1571459/pexels-photo-1571459.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1","Salon"))
                        libraryDao.insertPhoto(EstatePhoto(3L,"https://images.pexels.com/photos/53610/large-home-residential-house-architecture-53610.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Façade"))
                        libraryDao.insertPhoto(EstatePhoto(3L,"https://images.pexels.com/photos/2251247/pexels-photo-2251247.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1","Salon"))
                        libraryDao.insertPhoto(EstatePhoto(4L,"https://images.pexels.com/photos/259588/pexels-photo-259588.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Façade"))
                        libraryDao.insertPhoto(EstatePhoto(4L,"https://images.pexels.com/photos/2724749/pexels-photo-2724749.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1","Cuisine"))
                        libraryDao.insertPhoto(EstatePhoto(5L,"https://images.pexels.com/photos/208736/pexels-photo-208736.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Façade"))
                        libraryDao.insertPhoto(EstatePhoto(5L,"https://images.pexels.com/photos/1080721/pexels-photo-1080721.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1","Cuisine"))
                        libraryDao.insertPhoto(EstatePhoto(6L,"https://images.pexels.com/photos/323775/pexels-photo-323775.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Façade"))
                        libraryDao.insertPhoto(EstatePhoto(6L,"https://images.pexels.com/photos/1457841/pexels-photo-1457841.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1","Salon"))
                        libraryDao.insertPhoto(EstatePhoto(7L,"https://images.pexels.com/photos/106399/pexels-photo-106399.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Façade"))
                        libraryDao.insertPhoto(EstatePhoto(7L,"https://images.pexels.com/photos/1648776/pexels-photo-1648776.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1","Salon"))
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