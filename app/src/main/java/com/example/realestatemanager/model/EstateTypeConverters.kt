package com.example.realestatemanager.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EstateTypeConverters {
    @TypeConverter
    fun fromList(value: List<EstatePhoto>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toList(value: String?): List<EstatePhoto>? {
        return Gson().fromJson(value, object : TypeToken<List<EstatePhoto>>() {}.type)
    }
}