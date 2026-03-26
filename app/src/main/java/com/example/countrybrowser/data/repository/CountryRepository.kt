package com.example.countrybrowser.data.repository

import com.example.countrybrowser.data.api.CountryApiService
import com.example.countrybrowser.data.model.Country
import com.example.countrybrowser.utils.Resource

class CountryRepository {
    private val api = CountryApiService.create()

    suspend fun getAllCountries(): Resource<List<Country>> {
        return try {
            val result = api.getAllCountries()
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}