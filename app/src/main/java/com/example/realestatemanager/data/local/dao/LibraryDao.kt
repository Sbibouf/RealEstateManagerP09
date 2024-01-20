package com.example.realestatemanager.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstatePhoto
import com.example.realestatemanager.model.EstateWithPhotos
import kotlinx.coroutines.flow.Flow


@Dao
interface LibraryDao {

    /**
     * Insert new estate
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(estate : Estate)

    /**
     * Insert a new estatePhoto
     */
    @Insert
    fun insertPhoto(photo : EstatePhoto)

    /**
     * Add photo to estate and return all EstateWithPhoto
     */
    @Transaction
    @Query("SELECT * FROM estate")
    fun getAllEstateWithPhoto() : Flow<List<EstateWithPhotos>>

    /**
     * Add photo to estate and return EstateWithPhoto
     */
    @Transaction
    @Query("SELECT * FROM estate WHERE id = :id")
    fun getEstateWithPhotoById(id : Long): Flow<EstateWithPhotos>


    /**
     * Select Estate by its Id
     */

    @Query("SELECT * FROM Estate WHERE id = :estateId")
    fun getEstatebyId(estateId: Long?): LiveData<Estate>


    /**
     * Get all Estates from base
     * @return
     */
    @Query("SELECT * FROM Estate")
    fun getAllEstate(): Flow<List<Estate>>


    /**
     * Delete Estate from base
     *
     * @param EstateId
     * @return
     */
    @Query("DELETE FROM Estate WHERE id = :estateId")
    fun deleteEstate(estateId: Long?)


    /**
     * Update Estate with new data
     */
    @Update
    fun update(estate: Estate)










}