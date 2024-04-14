package com.example.petprojweather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(val location: MyLocation, val current: Current, val forecast: Forecast)

data class MyLocation(val name: String, val region: String, val country: String, val localtime: String)

data class Current(@SerializedName("last_updated") val lastUpdatedTime: String,
                   @SerializedName("temp_c") val temp: String,
                   @SerializedName("is_day") val dayOrNot: String,
                   @SerializedName("condition") val currentCondition: CurrentCondition,
                   @SerializedName("wind_kph") val windSpeed: String,
                   @SerializedName("feelslike_c") val feelsLikeTemp: String,
                   val humidity : String,
                   val uv: String
)

data class CurrentCondition (val text: String, val icon: String)

data class Forecast(@SerializedName("forecastday") val forecastDay: ArrayList<Daily>)

data class Daily(val date: String, val hour : ArrayList<Hourly>)

data class Hourly (val time: String, @SerializedName("temp_c") val tempHourly: String, val condition: CurrentCondition )
