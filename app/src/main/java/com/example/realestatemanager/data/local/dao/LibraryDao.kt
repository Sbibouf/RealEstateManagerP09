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
    fun insert(estate: Estate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEstate(estate: Estate)

    /**
     * Insert Estate in database and return ID
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun getInsertedEstateId(estate: Estate): Long


    /**
     * Insert a new estatePhoto
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photo: EstatePhoto)

    /**
     * Add photo to estate and return all EstateWithPhoto
     */
    @Transaction
    @Query("SELECT * FROM estate")
    fun getAllEstateWithPhoto(): Flow<List<EstateWithPhotos>>

    /**
     * Add photo to estate and return EstateWithPhoto
     */
    @Transaction
    @Query("SELECT * FROM estate WHERE id = :id")
    fun getEstateWithPhotoById(id: Long): Flow<EstateWithPhotos>


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

    @Query("DELETE FROM EstatePhoto WHERE id = :estatePhotoId ")
    suspend fun deleteEstatePhoto(estatePhotoId: Long?)


    /**
     * Update Estate with new data
     */
    @Update
    fun update(estate: Estate)

    /**
     * Query for the search engine
     * get the Estate with provided parameters or default
     */
    @Transaction
    @Query("SELECT * FROM Estate WHERE price = :estatePrice AND type = :estateType AND size = :estateSize AND numberOfRooms = :estateNumberOfRooms AND numberOfBedrooms = :estateNumberOfBedrooms AND numberOfBathrooms = :estateNumberOfBathrooms AND soldState = :estateSoldState AND address = :estateAddress AND entryDate = :estateEntryDate AND soldState = :estateSoldDate AND school = :estateSchool AND shops = :estateShops AND parc = :estateParc AND hospital = :estateHospital AND restaurant = :estateRestaurant AND sport = :estateSport")
    fun getEstateFromSearch(
        estatePrice: String?,
        estateType: String?,
        estateSize: String?,
        estateNumberOfRooms: String?,
        estateNumberOfBedrooms: String?,
        estateNumberOfBathrooms: String?,
        estateSoldState: String?,
        estateAddress: String?,
        estateEntryDate: String?,
        estateSoldDate: String?,
        estateSchool: String?,
        estateShops: String?,
        estateParc: String?,
        estateHospital: String?,
        estateRestaurant: String?,
        estateSport: String?
    ): Flow<List<EstateWithPhotos>>


}