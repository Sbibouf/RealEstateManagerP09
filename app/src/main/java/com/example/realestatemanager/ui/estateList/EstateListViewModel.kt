package com.example.realestatemanager.ui.estateList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanager.data.local.repository.EstateRepository
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstateWithPhotos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class EstateListViewModel @Inject constructor(private val estateRepository: EstateRepository, private val executor: Executor = Executors.newSingleThreadExecutor()) : ViewModel() {

val uiState : StateFlow<List<EstateWithPhotos>> = estateRepository.getAllEstateWithPhoto().stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = emptyList())
val uiStateLandscape : StateFlow<EstateWithPhotos?> = estateRepository.getEstateWithPhotoById(1L).stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = null)


}