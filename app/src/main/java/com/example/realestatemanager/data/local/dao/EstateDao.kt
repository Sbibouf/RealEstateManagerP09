package com.example.realestatemanager.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.realestatemanager.model.Estate
import kotlinx.coroutines.flow.Flow


@Dao
interface EstateDao {

    /**
     * Insert new estate
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(estate : Estate)


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
     * Insert a new estate into database
     * @param estate
     */
    @Insert
    fun createEstate(estate: Estate)

    /**
     * Update Estate with new data
     */
    @Update
    fun update(estate: Estate)

    /**
     * Check if the database is prepopulate
     */
    @Query("SELECT COUNT(*) FROM estate WHERE isPrepopulated = 1")
    fun isDatabasePrepopulate() : Boolean

    /**
     * Mark the database as prepopulate
     */
    @Query("UPDATE estate SET isPrepopulated = 1 WHERE isPrepopulated = 0")
    fun markDatabasePrepopulated()










}