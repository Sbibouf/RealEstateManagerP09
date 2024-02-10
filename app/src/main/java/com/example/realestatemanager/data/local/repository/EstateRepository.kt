package com.example.realestatemanager.data.local.repository

import com.example.realestatemanager.data.local.dao.LibraryDao
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstatePhoto
import com.example.realestatemanager.model.EstateWithPhotos
import com.example.realestatemanager.model.SearchCriteria
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EstateRepository @Inject constructor(private val libraryDao: LibraryDao) {


    fun insert(estate: Estate) = libraryDao.insert(estate)

    suspend fun insertEstate(estate: Estate) = libraryDao.insertEstate(estate)

    suspend fun deleteEstatePhoto(estatePhotoId: Long?) =
        libraryDao.deleteEstatePhoto(estatePhotoId)

    suspend fun getInsertedEstateId(estate: Estate): Long = libraryDao.getInsertedEstateId(estate)

    fun insertEstatePhoto(photo: EstatePhoto) = libraryDao.insertPhoto(photo)

    fun getAllEstateWithPhoto(): Flow<List<EstateWithPhotos>> = libraryDao.getAllEstateWithPhoto()

    fun getEstateFromSearchCriteria(searchCriteria: SearchCriteria) : Flow<List<EstateWithPhotos>> = libraryDao.getEstateFromSearchCriteria(searchCriteria.minPrice, searchCriteria.maxPrice, searchCriteria.minSize, searchCriteria.maxSize, searchCriteria.type, searchCriteria.minNumberOfRooms, searchCriteria.maxNumberOfRooms, searchCriteria.minNumberOfBedrooms, searchCriteria.maxNumberOfBedrooms, searchCriteria.minNumberOfBathrooms, searchCriteria.maxNumberOfBathrooms, searchCriteria.school, searchCriteria.shops, searchCriteria.parc, searchCriteria.hospital, searchCriteria.restaurant, searchCriteria.sport, searchCriteria.soldState, searchCriteria.entryDateMilli )

    fun getEstateWithPhotoById(id: Long): Flow<EstateWithPhotos> =
        libraryDao.getEstateWithPhotoById(id)

}