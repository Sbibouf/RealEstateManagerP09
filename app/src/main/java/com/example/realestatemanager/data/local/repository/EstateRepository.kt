package com.example.realestatemanager.data.local.repository

import androidx.lifecycle.LiveData
import com.example.realestatemanager.data.local.dao.EstateDao
import com.example.realestatemanager.model.Estate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EstateRepository @Inject constructor(private val estateDao: EstateDao){


    fun insertEstate(estate : Estate) = estateDao.insert(estate)

    fun getEstateById(id : Long) : LiveData<Estate> = estateDao.getEstatebyId(id)

    fun deleteEstateById(id : Long) = estateDao.deleteEstate(id)

    fun getAllEstates() : Flow<List<Estate>> = estateDao.getAllEstate()


    fun updateEstate(estate: Estate) = estateDao.update(estate)
}