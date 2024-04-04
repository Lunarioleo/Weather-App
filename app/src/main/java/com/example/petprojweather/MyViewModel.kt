package com.example.petprojweather

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MyViewModel: ViewModel() {
    private val _state = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _state
    private val repo = MyApplication.getInstance().repo



    private val _listState = MutableLiveData<ListState>(ListState.EmptyList)
    val listState: LiveData<ListState> = _listState

    private val observer = Observer<List<City>> {
        _listState.postValue(ListState.UpdatedList(list = it))
    }

    init {
        repo.getAll().observeForever(observer)

    }

    fun addCity(cityName:String){
        repo.add(City(cityName = cityName ))
    }
    fun removeCity(city: City){
        repo.delete(city)
    }
    override fun onCleared() {
        repo.getAll().removeObserver(observer)
        super.onCleared()
    }


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



    fun getListForecast(cityName: List<City>) {
        val tempMap = mutableMapOf<City, WeatherResponse>() // MutableMap to store cityName to WeatherResponse mapping
        viewModelScope.launch {
            // Asynchronously fetch weather data for each city
            cityName.forEach { city ->
                val weatherData = async { repo.getWeather(city.cityName) }.await()
                val body = weatherData.body()
                if (body != null) {
                    tempMap[city] = body // Update the map with cityName as key and WeatherResponse as value
                }
            }
            _state.postValue(UiState.ResultCards(tempMap.toMap())) // Post the ResultMap to UI state
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
        val task = fusedLocationProviderClient.lastLocation
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
        task.addOnSuccessListener {
            try {  // найти способ сделать без блока trycatch(более по-котлиновски)
                val currentLocation = "${it.latitude},${it.longitude}"
                getData(currentLocation)
            } catch (e: NullPointerException){
                Toast.makeText(MainActivity.instance, "Enable location!", Toast.LENGTH_SHORT).show()
            }
       }
    }
    sealed class UiState {

        class Result (val weatherResponse: WeatherResponse, val time: String?): UiState()

        class ResultCards(val weatherResponses: Map<City, WeatherResponse>):UiState()
    }

    sealed class ListState {
        object EmptyList:ListState()
        class UpdatedList(val list:List<City>):ListState()

    }
}