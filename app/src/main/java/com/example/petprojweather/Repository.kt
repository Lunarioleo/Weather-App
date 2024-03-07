package com.example.petprojweather

import android.os.Build
import androidx.annotation.RequiresApi
import retrofit2.Response
import retrofit2.Retrofit
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class Repository(private val client: Retrofit) {

    suspend fun getWeather(): Response<WeatherResponse> {
        val apiInterface = client.create(ApiInterface::class.java)
        return apiInterface.getWeather()
    }

}