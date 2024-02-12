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
//****************************************
// Module DAO provides instance of libraryDao and EstateDatabase
//****************************************
@Module
@InstallIn(SingletonComponent::class)
class DaoModule {
    lateinit var database: EstateDatabase
    @Provides
    fun provideEstateDao(@ApplicationContext context: Context): EstateDatabase {
        database = Room.databaseBuilder(
            context,
            EstateDatabase::class.java,
            "EstateDatabase.db"
        )
            /**
             * Prepopulate database with some estates and photos
             */
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val libraryDao = database.libraryDao()
                    val executor = database.transactionExecutor
                    executor.execute {
                        libraryDao.insert(Estate("House", "25000000", "225", "5","2","1", "Ce petit texte décrit le bien immobilier","City Hall, New York",  "40.713282","-74.006978", false, "21/12/2023", "","",true,true,true,false,false,false,1703113200000L))
                        libraryDao.insert(Estate("Penthouse", "20000000", "225", "5","2","1", "Ce petit texte décrit le bien immobilier","Brookyln",  "40.6526006","-73.9497211", false, "12/10/2023", "21/12/2023","",true,true,false,true,false,false,1697061600000L))
                        libraryDao.insert(Estate("House", "15000000", "225", "5","2","1", "Ce petit texte décrit le bien immobilier","Southampton",  "40.884267","-72.3895296", true, "10/11/2023", "21/11/2023","",false,true,false,true,true,false,1699570800000L))
                        libraryDao.insert(Estate("House", "17000000", "225", "5","2","1", "Ce petit texte décrit le bien immobilier","Upper East Side", "40.7735649","-73.9565551", false, "10/11/2023", "","",false,true,true,false,false,false,1699570800000L))
                        libraryDao.insert(Estate("House", "20000000", "225", "5","2","1", "Ce petit texte décrit le bien immobilier","Hampton Bays", "40.8689892","-72.5175893", true, "21/12/2023", "22/12/2023","",false,false,false,false,false,false,1703113200000L))
                        libraryDao.insert(Estate("House", "22000000", "225", "5","2","1", "Ce petit texte décrit le bien immobilier","Manhattan",  "40.776676","-73.971321", false, "21/12/2023", "","",true,true,true,true,true,true,1703113200000L))
                        libraryDao.insert(Estate("House", "35000000", "150", "5","2","1", "Ce petit texte décrit le bien immobilier","Montauk",  "41.0347223","-71.9442623", false, "21/12/2023", "","",false,true,true,false,false,true,1703113200000L))
                        libraryDao.insert(Estate("House", "35000000", "250", "5","2","1", "Ce petit texte décrit le bien immobilier","Staten Isla",  "","", false, "21/12/2023", "","",false,false,false,false,false,false,1703113200000L))


                        libraryDao.insertPhoto(EstatePhoto(1L,"https://images.pexels.com/photos/259593/pexels-photo-259593.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Façade"))
                        libraryDao.insertPhoto(EstatePhoto(1L,"https://images.pexels.com/photos/3701434/pexels-photo-3701434.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1","Salon"))
                        libraryDao.insertPhoto(EstatePhoto(2L,"https://images.pexels.com/photos/323780/pexels-photo-323780.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1","Façade"))
                        libraryDao.insertPhoto(EstatePhoto(2L,"https://images.pexels.com/photos/1571459/pexels-photo-1571459.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1","Salon"))
                        libraryDao.insertPhoto(EstatePhoto(3L,"https://images.pexels.com/photos/53610/large-home-residential-house-architecture-53610.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Façade"))
                        libraryDao.insertPhoto(EstatePhoto(3L,"https://images.pexels.com/photos/2251247/pexels-photo-2251247.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1","Salon"))
                        libraryDao.insertPhoto(EstatePhoto(4L,"https://images.pexels.com/photos/1438832/pexels-photo-1438832.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Façade"))
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
        return database
    }

    @Provides
    fun provideLibraryDao(database: EstateDatabase): LibraryDao {
        return database.libraryDao()
    }
    @Provides
    fun provideExecutor(): Executor{
        return Executors.newSingleThreadExecutor()
    }

}