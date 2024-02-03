package com.example.realestatemanager.data.local.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.realestatemanager.data.local.dao.LibraryDao
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstatePhoto
import com.example.realestatemanager.model.EstateWithPhotos
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EstateRepository @Inject constructor(private val libraryDao: LibraryDao){


    fun insertEstate(estate : Estate) = libraryDao.insert(estate)

    suspend fun getInsertedEstateId(estate : Estate) : Long = libraryDao.getInsertedEstateId(estate)

    fun insertEstatePhoto(photo: EstatePhoto) = libraryDao.insertPhoto(photo)

    fun getAllEstateWithPhoto() : Flow<List<EstateWithPhotos>> = libraryDao.getAllEstateWithPhoto()

    fun getEstateWithPhotoById(id : Long) : Flow<EstateWithPhotos> = libraryDao.getEstateWithPhotoById(id)

    fun getEstateById(id : Long) : LiveData<Estate> = libraryDao.getEstatebyId(id)

    fun deleteEstateById(id : Long) = libraryDao.deleteEstate(id)

    fun getAllEstates() : Flow<List<Estate>> = libraryDao.getAllEstate()


    fun updateEstate(estate: Estate) = libraryDao.update(estate)
}