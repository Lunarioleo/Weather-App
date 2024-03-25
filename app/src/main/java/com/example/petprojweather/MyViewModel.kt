package com.example.petprojweather

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MyViewModel: ViewModel() {
    private val _state = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _state
    private val repo = MyApplication.getInstance().repo
    fun getData(cityName: String){
        viewModelScope.launch {
            val weatherData = async { repo.getWeather(cityName) }.await()
            var body = weatherData.body()
            var a = body?.location?.localtime?.substring(10,11)
            if (body  != null){
                _state.postValue(UiState.Result(body, a))
            }
        }
    }

     fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                MainActivity.instance,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                MainActivity.instance,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                MainActivity.instance,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1001
            )
            return
        }
    }




    fun fetchLocation() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.instance)
        if (ActivityCompat.checkSelfPermission(
                MainActivity.instance,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MainActivity.instance,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                MainActivity.instance,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        }
        fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                val currentLocation = "${it.result.latitude},${it.result.longitude}"
                getData(currentLocation)

       }


    }




    sealed class UiState {
        class Result (val weatherResponse: WeatherResponse, val time: String?): UiState()
    }
}