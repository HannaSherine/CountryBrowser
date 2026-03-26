package com.example.countrybrowser.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countrybrowser.data.model.Country
import com.example.countrybrowser.data.repository.CountryRepository
import com.example.countrybrowser.utils.Resource
import kotlinx.coroutines.launch

class CountryListViewModel : ViewModel() {

    private val repository = CountryRepository()

    private val _countries = MutableLiveData<Resource<List<Country>>>()
    val countries: LiveData<Resource<List<Country>>> = _countries

    // Holds the full list so search doesn't re-fetch
    private var allCountries: List<Country> = emptyList()

    init {
        fetchCountries()
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            _countries.value = Resource.Loading()
            val result = repository.getAllCountries()
            if (result is Resource.Success) {
                allCountries = result.data.sortedBy { it.name.common }
            }
            _countries.value = result
        }
    }

    fun search(query: String) {
        if (query.isBlank()) {
            _countries.value = Resource.Success(allCountries)
            return
        }
        val filtered = allCountries.filter {
            it.name.common.contains(query, ignoreCase = true)
        }
        _countries.value = Resource.Success(filtered)
    }
}