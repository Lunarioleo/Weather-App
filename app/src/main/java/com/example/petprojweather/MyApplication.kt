package com.example.petprojweather

import android.app.Application
import androidx.room.Room
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class MyApplication: Application() {
    lateinit var  repo: Repository
    override fun onCreate() {
        instance = this
        super.onCreate()
        val database = Room.databaseBuilder(this, CitiesDatabase::class.java, "cities_database").build()
        repo = Repository(getApiClient(), database)
    }

        private fun getApiClient(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    companion object {
         private lateinit var instance: MyApplication
        fun getInstance() = instance
    }
}