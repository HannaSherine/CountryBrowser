package com.example.countrybrowser.data.api

import com.example.countrybrowser.data.model.Country
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CountryApiService {

    @GET("all?fields=name,capital,region,population,area,flags,languages")
    suspend fun getAllCountries(): List<Country>

    companion object {
        private const val BASE_URL = "https://restcountries.com/v3.1/"

        fun create(): CountryApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CountryApiService::class.java)
        }
    }
}