package com.example.realestatemanager.ui.addEstate

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.realestatemanager.BuildConfig
import com.example.realestatemanager.data.local.repository.EstateRepository
import com.example.realestatemanager.data.local.service.Utils
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstatePhoto
import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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


    val _photoList: MutableStateFlow<MutableList<EstatePhoto>> = MutableStateFlow(
        mutableListOf(EstatePhoto())
    )

    val photoList: StateFlow<List<EstatePhoto>> get() = _photoList


    fun updateEstate(transform: Estate.() -> Estate) {
        _estate.value = transform.invoke(_estate.value)
    }

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

    private suspend fun geocodeAddressSuspended(address: String?): LatLng? {
        return withContext(Dispatchers.IO) {
            geocodeAddressInBackground(address)
        }
    }

    suspend fun geocodeAndInserts(estate: Estate) {
        if (Utils.isInternetAvailable(application)) {
            val location = geocodeAddressSuspended(estate.address)
            if (location != null) {
                insertEstateWithLatitudeAndLongitude(location, estate)
            } else {
                insertHoleEstate(estate)
                Toast.makeText(
                    application,
                    "L'adresse renseignée ne permet pas de récupérer la localisation du bien",
                    Toast.LENGTH_LONG
                ).show()
                // Affichez un message ici si nécessaire
            }
        } else {
            insertHoleEstate(estate)
            Toast.makeText(
                application,
                "Une connexion internet est requise pour récupérer la localisation du bien",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun insertHoleEstate(estate: Estate) {
        executor.execute {
            estateRepository.insertEstate(
                Estate(
                    estate.type,
                    estate.price,
                    estate.size,
                    estate.numberOfRooms,
                    estate.numberOfBedrooms,
                    estate.numberOfBathrooms,
                    estate.description,
                    estate.address,
                    estate.city,
                    estate.latitude,
                    estate.longitude,
                    estate.soldState,
                    estate.entryDate,
                    estate.soldDate,
                    estate.agent,
                    estate.school,
                    estate.shops,
                    estate.parc,
                    estate.hospital,
                    estate.restaurant,
                    estate.sport
                )
            )
        }
    }

    private fun insertEstateWithLatitudeAndLongitude(location: LatLng, estate: Estate) {
        executor.execute {
            estateRepository.insertEstate(
                Estate(
                    estate.type,
                    estate.price,
                    estate.size,
                    estate.numberOfRooms,
                    estate.numberOfBedrooms,
                    estate.numberOfBathrooms,
                    estate.description,
                    estate.address,
                    estate.city,
                    location.latitude.toString(),
                    location.longitude.toString(),
                    estate.soldState,
                    estate.entryDate,
                    estate.soldDate,
                    estate.agent,
                    estate.school,
                    estate.shops,
                    estate.parc,
                    estate.hospital,
                    estate.restaurant,
                    estate.sport
                )
            )
        }
    }

    fun replaceOdlPhotoByNewPhoto(
        photoLists: MutableList<EstatePhoto>,
        photo: EstatePhoto
    ) {
        val index = photoLists.indexOf(photo)
        if (index > -1) {
            photoLists[index] = photo
        }
        if(photoLists.size==0){
            photoLists.add(photo)
        }
        _photoList.value = photoLists
    }

    fun addPhoto(photo: EstatePhoto) {


        _photoList.value.add(photo)


    }


}