package com.example.realestatemanager.data.local.dao

import android.database.Cursor
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
     * Insert new estate use for prepopulate
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(estate: Estate): Long

    /**
     * Insert Estate or update it if it already exists in database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEstate(estate: Estate)

    /**
     * Insert Estate in database and return ID
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun getInsertedEstateId(estate: Estate): Long

    @Query("SELECT * FROM EstatePhoto WHERE uri = :uri")
    fun getPhotoByUri(uri: String): EstatePhoto?


    /**
     * Insert a new estatePhoto or update it if it already exists
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photo: EstatePhoto)

    fun insertOrUpdatePhoto(photo: EstatePhoto) {
        val existingPhoto = photo.uri?.let { getPhotoByUri(it) }
        if (existingPhoto != null) {

            photo.id = existingPhoto.id
            insertPhoto(photo)
        } else {

            insertPhoto(photo)
        }
    }

    /**
     * Get all the Estate with their photos from database
     */
    @Transaction
    @Query("SELECT * FROM estate")
    fun getAllEstateWithPhoto(): Flow<List<EstateWithPhotos>>


    /**
     * Delete an Estate Photo from database by its id
     */
    @Query("DELETE FROM EstatePhoto WHERE id = :estatePhotoId ")
    suspend fun deleteEstatePhoto(estatePhotoId: Long?)

    /**
     * Delete Estate from database, use for test
     */
    @Query("DELETE FROM Estate WHERE id = :estateId")
    fun deleteEstate(estateId: Long?): Int


    /**
     * Update Estate with new data
     */
    @Update
    fun updateEstate(estate: Estate): Int

    /**
     * Query for the search engine
     * get the Estate with provided parameters or default
     */

    @Transaction
    @Query(
        "SELECT * FROM Estate " +
                "WHERE (CAST(price AS INTEGER) BETWEEN :minPrice AND :maxPrice) " +
                "AND (CAST(size AS INTEGER) BETWEEN :minSize AND :maxSize) " +
                "AND (CAST(numberOfRooms AS INTEGER) BETWEEN :minNumberOfRooms AND :maxNumberOfRooms) " +
                "AND (CAST(numberOfBedrooms AS INTEGER) BETWEEN :minNumberOfBedrooms AND :maxNumberOfBedrooms) " +
                "AND (CAST(numberOfBathrooms AS INTEGER) BETWEEN :minNumberOfBathrooms AND :maxNumberOfBathrooms) " +
                "AND (:type = 'Tous les biens immobiliers' OR type = :type) " +
                "AND soldState != :soldState " +
                "AND ( (:school = :school AND school = true) OR (:school = false) )" +
                "AND ( (:shops = :shops AND shops = true) OR (:shops = false) )" +
                "AND ( (:parc = :parc AND parc = true) OR (:parc = false) ) " +
                "AND ( (:hospital = :hospital AND hospital = true) OR (:hospital = false) ) " +
                "AND ( (:restaurant = :restaurant AND restaurant = true) OR (:restaurant = false) ) " +
                "AND ( (:sport = :sport AND sport = true) OR (:sport = false) )" +
                "AND entryDateMilli >= :entryDateMilli"
    )
    fun getEstateFromSearchCriteria(
        minPrice: Int,
        maxPrice: Int,
        minSize: Int,
        maxSize: Int,
        type: String?,
        minNumberOfRooms: Int,
        maxNumberOfRooms: Int,
        minNumberOfBedrooms: Int,
        maxNumberOfBedrooms: Int,
        minNumberOfBathrooms: Int,
        maxNumberOfBathrooms: Int,
        school: Boolean?,
        shops: Boolean?,
        parc: Boolean?,
        hospital: Boolean?,
        restaurant: Boolean?,
        sport: Boolean?,
        soldState: Boolean?,
        entryDateMilli: Long?
    ): Flow<List<EstateWithPhotos>>


    @Query("SELECT * FROM Estate WHERE Id = :estateId")
    fun getEstateWithCursor(estateId: Long): Cursor

}