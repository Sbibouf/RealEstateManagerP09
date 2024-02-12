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
        "SELECT Estate.*, COUNT(EstatePhoto.id) AS photoCount FROM Estate " +
                "LEFT JOIN EstatePhoto ON Estate.id = EstatePhoto.estateId " +
                "WHERE (CAST(Estate.price AS INTEGER) BETWEEN :minPrice AND :maxPrice) " +
                "AND (CAST(Estate.size AS INTEGER) BETWEEN :minSize AND :maxSize) " +
                "AND (CAST(Estate.numberOfRooms AS INTEGER) BETWEEN :minNumberOfRooms AND :maxNumberOfRooms) " +
                "AND (CAST(Estate.numberOfBedrooms AS INTEGER) BETWEEN :minNumberOfBedrooms AND :maxNumberOfBedrooms) " +
                "AND (CAST(Estate.numberOfBathrooms AS INTEGER) BETWEEN :minNumberOfBathrooms AND :maxNumberOfBathrooms) " +
                "AND (:type = 'Tous les biens immobiliers' OR Estate.type = :type) " +
                "AND Estate.soldState != :soldState " +
                "AND ( (:school = :school AND Estate.school = true) OR (:school = false) )" +
                "AND ( (:shops = :shops AND Estate.shops = true) OR (:shops = false) )" +
                "AND ( (:parc = :parc AND Estate.parc = true) OR (:parc = false) ) " +
                "AND ( (:hospital = :hospital AND Estate.hospital = true) OR (:hospital = false) ) " +
                "AND ( (:restaurant = :restaurant AND Estate.restaurant = true) OR (:restaurant = false) ) " +
                "AND ( (:sport = :sport AND Estate.sport = true) OR (:sport = false) )" +
                "AND Estate.entryDateMilli >= :entryDateMilli " +
                "GROUP BY Estate.id " +
                "HAVING photoCount >= :photoCount"
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
        entryDateMilli: Long?,
        photoCount : Int
    ): Flow<List<EstateWithPhotos>>


    @Query("SELECT * FROM Estate WHERE Id = :estateId")
    fun getEstateWithCursor(estateId: Long): Cursor

}