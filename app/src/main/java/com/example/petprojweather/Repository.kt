package com.example.petprojweather

import retrofit2.Response
import retrofit2.Retrofit

class Repository(private val client: Retrofit) {

    suspend fun getWeather(cityName: String): Response<WeatherResponse> {
        val apiInterface = client.create(ApiInterface::class.java)
        return apiInterface.getWeather(cityName = cityName)
    }

}