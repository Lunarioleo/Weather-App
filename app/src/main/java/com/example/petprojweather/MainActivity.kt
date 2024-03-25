package com.example.petprojweather


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.petprojweather.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayout
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var fragmentAdapter: VPAdapter

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var myViewModel: MyViewModel

    companion object {
        lateinit var instance: MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        instance = this
        setContentView(binding.root)
        myViewModel = ViewModelProvider(this)[MyViewModel::class.java]


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        fragmentAdapter = VPAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = fragmentAdapter
        binding.viewPager.isUserInputEnabled = false



        binding.location.setOnClickListener {
            myViewModel.checkPermissions()
            myViewModel.fetchLocation()
        }




        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.viewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })

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
                    binding.currentWeatherDesc.text =
                        it.weatherResponse.current.currentCondition.text
                    val sdf = SimpleDateFormat("EEE, dd MMM", Locale.ENGLISH)
                    binding.currentData.text = sdf.format(Calendar.getInstance().time)
                    binding.feelingTemperature.text = it.weatherResponse.current.feelsLikeTemp.toDouble().toInt().toString() + "°"
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


