package com.example.petprojweather

import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

@GET ("forecast?latitude=51.7638&longitude=19.5046&current=temperature_2m,precipitation&hourly=temperature_2m,is_day&daily=sunrise,sunset&timezone=Europe%2FBerlin&forecast_days=1&forecast_hours=6&models=best_match")
    suspend fun getWeather(): Response<MainActivity.Weather>
}