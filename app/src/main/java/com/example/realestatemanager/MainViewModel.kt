package com.example.realestatemanager

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.realestatemanager.data.local.repository.EstateRepository

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

class MainViewModel @Inject constructor(private val estateRepository: EstateRepository) : ViewModel() {




}