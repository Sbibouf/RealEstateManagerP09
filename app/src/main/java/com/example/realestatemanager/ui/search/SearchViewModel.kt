package com.example.realestatemanager.ui.search

import androidx.lifecycle.ViewModel
import com.example.realestatemanager.data.local.repository.EstateRepository
import com.example.realestatemanager.model.Estate
import com.example.realestatemanager.model.EstateWithPhotos
import com.example.realestatemanager.model.SearchCriteria
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val estateRepository: EstateRepository
) : ViewModel() {

    private val _search: MutableStateFlow<SearchCriteria> = MutableStateFlow(SearchCriteria())
    val search: StateFlow<SearchCriteria> get() = _search


    fun updateEstate(transform: SearchCriteria.() -> SearchCriteria) {
        _search.value = transform.invoke(_search.value)
    }
}