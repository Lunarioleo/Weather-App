package com.example.petprojweather

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petprojweather.databinding.ActivityMainBinding
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso
import java.net.URL
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val myViewModel: MyViewModel = ViewModelProvider(this)[MyViewModel::class.java]
        myViewModel.getData()
        myViewModel.uiState.observe(this) {
            when (it) {
                is MyViewModel.UiState.Result -> {
                    val temp = it.weatherResponse.current.temp.toFloat().roundToInt()
                    binding.temperature.text = "${temp}°"
                    binding.currentCountry.text = it.weatherResponse.location.country
                    binding.currentCity.text = it.weatherResponse.location.name
                    Picasso.get()
                        .load("https:" + it.weatherResponse.current.currentCondition.icon)
                        .into(binding.mainCardImage)
                    binding.currentWeatherDesc.text = it.weatherResponse.current.currentCondition.text
                    binding.currentData.text = it.weatherResponse.location.localtime.subSequence(0, 10)
                    binding.feelingTemperature.text = it.weatherResponse.current.feelsLikeTemp + "°"
                    binding.windSpeed.text = it.weatherResponse.current.windSpeed + "km/h"
                    binding.humidity.text = it.weatherResponse.current.humidity + "%"

                }
            }
        }
    }

}

data class WeatherResponse(val location: MyLocation, val current: Current, val forecast: Forecast)

data class MyLocation(val name: String, val region: String, val country: String, val localtime: String)

data class Current(@SerializedName ("last_updated") val lastUpdatedTime: String,
                   @SerializedName ("temp_c") val temp: String,
                   @SerializedName ("is_day") val dayOrNot: String,
                   @SerializedName ("condition") val currentCondition: CurrentCondition,
                   @SerializedName ("wind_kph") val windSpeed: String,
                   @SerializedName ("feelslike_c") val feelsLikeTemp: String,
                   val humidity : String
    )

data class CurrentCondition (val text: String, val icon: String)

data class Forecast(@SerializedName ("forecastday") val forecastDay: ArrayList<Daily>)

data class Daily(val date: String, val hour : ArrayList<Hourly>)

data class Hourly (val time: String, @SerializedName("temp_c") val tempHourly: String, val condition: CurrentCondition )


