package com.example.realestatemanager.ui.search

import androidx.lifecycle.ViewModel
import com.example.realestatemanager.data.local.repository.EstateRepository
import com.example.realestatemanager.model.EstateWithPhotos
import com.example.realestatemanager.model.SearchCriteria
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val estateRepository: EstateRepository
) : ViewModel() {

//    fun getSearchResult(searchCriteria: SearchCriteria) : List<EstateWithPhotos>{
//        return estateRepository.getSearchResult(searchCriteria)
//    }
//
}