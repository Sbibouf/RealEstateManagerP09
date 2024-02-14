package com.example.realestatemanager.ui.addEstate

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanager.BuildConfig
import com.example.realestatemanager.data.local.repository.EstateRepository
import com.example.realestatemanager.data.local.service.Utils
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstatePhoto
import com.example.realestatemanager.model.EstateWithPhotos
import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject


@HiltViewModel
class AddEstateViewModel @Inject constructor(
    @ApplicationContext private val application: Context,
    private val estateRepository: EstateRepository,
    private val executor: Executor = Executors.newSingleThreadExecutor()
) : ViewModel() {

    private val _estate: MutableStateFlow<Estate> = MutableStateFlow(Estate())
    val estate: StateFlow<Estate> get() = _estate


    private val _photoList: MutableStateFlow<MutableList<EstatePhoto>> = MutableStateFlow(
        mutableListOf()
    )

    val photoList: StateFlow<List<EstatePhoto>> get() = _photoList

    fun setEstateWithPhoto(estateWithPhotos: EstateWithPhotos) {
        _estate.value = estateWithPhotos.estate!!
        _photoList.value = estateWithPhotos.photos as MutableList<EstatePhoto>
    }

    //***********************************************
    // Update Estate by making a copy of it in AddEstate form
    //***********************************************

    fun updateEstate(transform: Estate.() -> Estate) {
        _estate.value = transform.invoke(_estate.value)
    }


    //************************************************
    //Method that perform the Geocoding from address
    //************************************************
    private fun geocodeAddressInBackground(address: String?): LatLng? {
        val apiKey = BuildConfig.MAPS_API_KEY

        val urlString =
            "https://maps.googleapis.com/maps/api/geocode/json?address=$address&key=$apiKey"

        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val inputStreamReader = InputStreamReader(connection.inputStream)
            val jsonElement = JsonParser.parseReader(inputStreamReader)
            val jsonObject = jsonElement.asJsonObject

            if (jsonObject.has("results") && jsonObject.getAsJsonArray("results").size() > 0) {
                val location =
                    jsonObject.getAsJsonArray("results")[0].asJsonObject.getAsJsonObject("geometry")
                        .getAsJsonObject("location")

                val latitude = location.getAsJsonPrimitive("lat").asDouble
                val longitude = location.getAsJsonPrimitive("lng").asDouble

                return LatLng(latitude, longitude)
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }


    //*****************************************
    // Do the Geocoding in a coroutine scope
    //*****************************************

    private suspend fun geocodeAddressSuspended(address: String?): LatLng? {
        return withContext(Dispatchers.IO) {
            geocodeAddressInBackground(address)
        }
    }

    //**************************************************
    // Get the address, check if there is a network connection, perform geocoding and insert Estate accordingly
    //**************************************************
    suspend fun geocodeAndInserts(estate: Estate, photos: List<EstatePhoto>) {

        if (Utils.isInternetAvailable(application)) {
            val location = geocodeAddressSuspended(estate.address)
            if (location != null) {
                insertEstateWithLatitudeAndLongitude(location, estate, photos)
                Toast.makeText(
                    application,
                    "Le bien a été géolocalisé et ajouté à liste",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                insertEstateAndPhotos(estate, photos)
                Toast.makeText(
                    application,
                    "Le bien à été ajouté mais l'adresse renseignée ne permet pas de récupérer sa localisation",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            insertEstateAndPhotos(estate, photos)
            Toast.makeText(
                application,
                "Le bien à été ajouté mais une connexion internet est requise pour récupérer sa localisation",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    //****************************************
    // Add Latitude and Longitude information into Estate and insert it into database
    //****************************************
    private suspend fun insertEstateWithLatitudeAndLongitude(
        location: LatLng,
        estate: Estate,
        photos: List<EstatePhoto>
    ) {

        estate.latitude = location.latitude.toString()
        estate.longitude = location.longitude.toString()

        insertEstateAndPhotos(estate, photos)
    }


    //*******************************************
    // Add photo to list if its not already in
    //*******************************************
    fun addPhoto(newPhoto: EstatePhoto) {
        val containsUri = _photoList.value.any { it.uri == newPhoto.uri }
        if (!containsUri) {
            _photoList.value = _photoList.value.toMutableList().apply { add(newPhoto) }
        } else {
            Toast.makeText(application, "Cette photo est déjà dans la liste", Toast.LENGTH_LONG)
                .show()
        }

    }

    //******************************************
    // Delete photo from list and from database
    //******************************************

    fun deletePhoto(photo: EstatePhoto) {
        _photoList.value = _photoList.value.toMutableList().apply { remove(photo) }
        viewModelScope.launch {
            estateRepository.deleteEstatePhoto(photo.id)
        }
    }

    //****************************************
    //Replace old photo by new one with new name in list
    //*****************************************
    fun replaceOldPhotoByNewPhoto(uri: Uri, newPhoto: EstatePhoto) {
        val mutableList = _photoList.value.toMutableList()
        val index = mutableList.indexOfFirst { it.uri == uri.toString() }

        if (index != -1) {
            mutableList[index] = newPhoto
            _photoList.value = mutableList
        }
    }

    //********************************************
    // Check if estate exist then get its id and add it to list and insert into database
    //********************************************

    private suspend fun insertEstateAndPhotos(estate: Estate, photos: List<EstatePhoto>) {

        try {
            if (estate.id == 0L) {
                val estateId = estateRepository.getInsertedEstateId(estate)
                Log.d("InsertEstate", "Inserted Estate ID: $estateId")

                if (photos.isNotEmpty()) {
                    val photosWithEstateId = photos.map { it.copy(estateId = estateId) }

                    for (estatePhoto in photosWithEstateId) {
                        executor.execute {
                            estateRepository.insertEstatePhoto(estatePhoto)
                        }
                    }
                }
            } else {
                estateRepository.insertEstate(estate)
                for (estatePhoto in photos) {
                    executor.execute {
                        estateRepository.insertEstatePhoto(estatePhoto)
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("InsertEstate", "Error inserting estate with photos", e)
        }


    }
}