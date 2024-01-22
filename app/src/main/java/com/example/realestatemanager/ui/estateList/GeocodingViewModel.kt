package com.example.realestatemanager.ui.estateList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class GeocodingViewModel : ViewModel() {

    private var _locationState : MutableStateFlow<LatLng?> = MutableStateFlow(null)
    val locationState: StateFlow<LatLng?> get() = _locationState

    fun geocodeAddress(address: String?) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                geocodeAddressInBackground(address)
            }
            _locationState.value = result

        }

    }

    private fun geocodeAddressInBackground(address: String?): LatLng? {
        val apiKey = "AIzaSyA35rky_HYWt623gjs_5I3vCYUbBaxEilE" // Remplacez par votre clé API

        val urlString = "https://maps.googleapis.com/maps/api/geocode/json?address=$address&key=$apiKey"

        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val inputStreamReader = InputStreamReader(connection.inputStream)
            val jsonElement = JsonParser.parseReader(inputStreamReader)
            val jsonObject = jsonElement.asJsonObject

            if (jsonObject.has("results") && jsonObject.getAsJsonArray("results").size() > 0) {
                val location = jsonObject.getAsJsonArray("results")[0].asJsonObject.getAsJsonObject("geometry")
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
}