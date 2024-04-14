package com.example.petprojweather

import retrofit2.Response
import retrofit2.Retrofit
import java.util.concurrent.Executors

class Repository(private val client: Retrofit, private val database: CitiesDatabase) {
    private val executor = Executors.newSingleThreadExecutor()
    suspend fun getWeather(cityName: String): Response<WeatherResponse> {
        val apiInterface = client.create(ApiInterface::class.java)
        return apiInterface.getWeather(cityName = cityName)
    }

    fun getAll() =  database.cityDao().getAll()


    fun add(city: City) {
        executor.execute {
            database.cityDao().add(city)
        }
    }

    fun delete(city: City){
        executor.execute {
            database.cityDao().delete(city)
        }
    }



}