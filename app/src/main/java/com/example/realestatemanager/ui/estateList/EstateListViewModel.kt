package com.example.realestatemanager.ui.estateList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanager.data.local.repository.EstateRepository
import com.example.realestatemanager.model.EstateWithPhotos
import com.example.realestatemanager.model.SearchCriteria
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstateListViewModel @Inject constructor(
    private val estateRepository: EstateRepository
) : ViewModel() {
    var searchPerformed : MutableStateFlow<Boolean> = MutableStateFlow(false)

    val uiState: StateFlow<List<EstateWithPhotos>> = estateRepository.getAllEstateWithPhoto()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )


    private val _estateWithPhotoList : MutableStateFlow<List<EstateWithPhotos>> = MutableStateFlow(
        emptyList()
    )

    val estatePhotoList : StateFlow<List<EstateWithPhotos>> get() = _estateWithPhotoList

    private val _estateWithPhoto: MutableStateFlow<EstateWithPhotos> =
        MutableStateFlow(EstateWithPhotos())
    val estateWithPhoto: StateFlow<EstateWithPhotos> get() = _estateWithPhoto

    fun setEstateWithPhoto(estateWithPhotos: EstateWithPhotos) {
        _estateWithPhoto.value = estateWithPhotos
    }

    fun getEstateWithPhotoFromSearch(searchCriteria: SearchCriteria)  {
        viewModelScope.launch {
            _estateWithPhotoList.value = estateRepository.getEstateFromSearchCriteria(searchCriteria).first()
            searchPerformed.value = true
        }

    }

    fun cancelSearch(){
        searchPerformed.value = false
    }


}