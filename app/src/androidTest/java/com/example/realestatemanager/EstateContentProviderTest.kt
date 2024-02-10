package com.example.realestatemanager

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.realestatemanager.data.local.database.EstateDatabase
import com.example.realestatemanager.provider.EstateContentProvider
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class EstateContentProviderTest {

    // FOR DATA

    private lateinit var mContentResolver: ContentResolver

    // DATA SET FOR TEST

    private val ESTATE_ID: Long = 100

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        Room.inMemoryDatabaseBuilder(context, EstateDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        mContentResolver = context.contentResolver
    }

    @Test
    fun getEstatesWhenNoEstateInserted() {
        val cursor = mContentResolver.query(
            ContentUris.withAppendedId(EstateContentProvider.URI_ESTATE, ESTATE_ID),
            null,
            null,
            null,
            null
        )

        assertThat(cursor, `is`(notNullValue()))
        assertThat(cursor?.count, `is`(0))
        cursor?.close()
    }

    @Test
    fun insertAndGetEstate() {
        // BEFORE : Adding demo estate
       mContentResolver.insert(EstateContentProvider.URI_ESTATE, generateEstate())

        // TEST
        val cursor = mContentResolver.query(
            ContentUris.withAppendedId(EstateContentProvider.URI_ESTATE, ESTATE_ID),
            null,
            null,
            null,
            null
        )

        assertThat(cursor, `is`(notNullValue()))
        assertThat(cursor?.count, `is`(1))
        assertThat(cursor?.moveToFirst(), `is`(true))
        assertThat(
            cursor?.getString(cursor.getColumnIndexOrThrow("type")),
            `is`("Maison de rêve !")
        )
        cursor?.close()
    }

    private fun generateEstate(): ContentValues {
        val values = ContentValues()
        values.put("type", "Maison de rêve !")
        values.put("price", "250000000")
        values.put("size", "250")
        values.put("numberOfRooms", "10")
        values.put("numberOfBedrooms", "5")
        values.put("numberOfBathrooms", "3")
        values.put("description", "Voici une maison de rêve")
        values.put("address", "21 jump street")
        values.put("city", "New York")
        values.put("latitude", "40.7127922")
        values.put("longitude", "-74.006110")
        values.put("soldState", "false")
        values.put("entryDate", "25/01/2024")
        values.put("soldDate", "")
        values.put("agent", "Me")
        values.put("school", "true")
        values.put("shops", "true")
        values.put("parc", "true")
        values.put("hospital", "true")
        values.put("restaurant", "true")
        values.put("sport", "true")
        values.put("id", "100")
        return values
    }
}