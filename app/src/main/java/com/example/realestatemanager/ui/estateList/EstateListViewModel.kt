package com.example.realestatemanager.ui.estateList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanager.data.local.repository.EstateRepository
import com.example.realestatemanager.model.EstateWithPhotos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class EstateListViewModel @Inject constructor(
    estateRepository: EstateRepository
) : ViewModel() {

    val uiState: StateFlow<List<EstateWithPhotos>> = estateRepository.getAllEstateWithPhoto()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    private val _estateWithPhoto : MutableStateFlow<EstateWithPhotos> = MutableStateFlow(EstateWithPhotos())
    val estateWithPhoto : StateFlow<EstateWithPhotos> get() = _estateWithPhoto

    fun setEstateWithPhoto(estateWithPhotos: EstateWithPhotos){
        _estateWithPhoto.value = estateWithPhotos
    }



}