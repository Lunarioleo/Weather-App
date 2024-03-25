package com.example.petprojweather

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

@GET ("v1/forecast.json?key=2529ca24381e43f586f150438242502")

    suspend fun getWeather(
    @Query("q")cityName: String,
    @Query("days") daysToForecast: String = "2",
    @Query("aqi") aqi: String = "no",
    @Query("alerts") alerts: String = "no"
    ): Response<WeatherResponse>
}