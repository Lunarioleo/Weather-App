package com.example.petprojweather

import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

@GET ("v1/forecast.json?key=2529ca24381e43f586f150438242502&q=Lodz&days=2&aqi=no&alerts=no")
    suspend fun getWeather(): Response<WeatherResponse>
}