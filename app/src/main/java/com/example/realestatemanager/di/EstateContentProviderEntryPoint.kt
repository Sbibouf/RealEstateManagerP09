package com.example.realestatemanager.di

import com.example.realestatemanager.data.local.database.EstateDatabase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//********************************************
// For ContentProvider Test
//********************************************
@EntryPoint
@InstallIn(SingletonComponent::class)
interface EstateContentProviderEntryPoint {
    fun getEstateDatabase(): EstateDatabase
}