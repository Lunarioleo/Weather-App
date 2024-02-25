package com.example.petprojweather

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petprojweather.databinding.ActivityMainBinding
import com.google.gson.annotations.SerializedName
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
                    binding.temperature.text = it.weatherResponse.current.temperature2M.toInt().toString() + it.weatherResponse.currentUnits.temperature2M
                    binding.currentData.text = it.weatherResponse.current.time.subSequence(0..9)
                    var adapter = RecyclerViewAdapter(
                        it.weatherResponse.hourly.time,
                        it.weatherResponse.hourly.temperature2M,
                        it.weatherResponse.hourly.isDay
                    )
                    binding.rcView.adapter = adapter
                    binding.rcView.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                }
            }
        }
    }

    data class Weather(
        val latitude: Double,
        val longitude: Double,

        @SerializedName("generationtime_ms")
        val generationtimeMS: Double,

        @SerializedName("utc_offset_seconds")
        val utcOffsetSeconds: Long,

        val timezone: String,

        @SerializedName("timezone_abbreviation")
        val timezoneAbbreviation: String,

        val elevation: Long,

        @SerializedName("current_units")
        val currentUnits: CurrentUnits,

        val current: Current,

        @SerializedName("hourly_units")
        val hourlyUnits: HourlyUnits,

        val hourly: Hourly,

        @SerializedName("daily_units")
        val dailyUnits: DailyUnits,

        val daily: Daily
    )

    data class Current(
        val time: String,
        val interval: String,

        @SerializedName("temperature_2m")
        val temperature2M: Double,

        val precipitation: String
    )

    data class CurrentUnits(
        val time: String,
        val interval: String,

        @SerializedName("temperature_2m")
        val temperature2M: String,

        val precipitation: String
    )

    data class Daily(
        val time: List<String>,
        val sunrise: List<String>,
        val sunset: List<String>
    )

    data class DailyUnits(
        val time: String,
        val sunrise: String,
        val sunset: String
    )

    data class Hourly(
        val time: List<String>,

        @SerializedName("temperature_2m")
        val temperature2M: List<Double>,

        @SerializedName("is_day")
        val isDay: List<Long>
    )

    data class HourlyUnits(
        val time: String,

        @SerializedName("temperature_2m")
        val temperature2M: String,

        @SerializedName("is_day")
        val isDay: String
    )
}