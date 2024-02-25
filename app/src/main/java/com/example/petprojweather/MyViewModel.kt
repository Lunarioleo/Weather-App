package com.example.petprojweather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MyViewModel: ViewModel() {
    private val _state = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _state
    private val repo = MyApplication.getInstance().repo

    fun getData(){
        viewModelScope.launch {
            val weatherData = async { repo.getWeather() }.await()
            if (weatherData.isSuccessful && weatherData.body() != null){
                _state.postValue(UiState.Result(weatherData.body()!!))
            }

        }
    }
    sealed class UiState {
        class Result (val weatherResponse: WeatherResponse): UiState()
       // class RcResult (val rcResultTime: List<String>?, val rcResultTemperature: List<Double>?, val rcResultDay: List<Long>?): UiState()
    }
}