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
            val weatherData = async { repo.getWeatger() }.await()
            if (weatherData.isSuccessful && weatherData.body() != null){
                _state.postValue(UiState.Result(weatherData.body()!!))
            }
            //_state.postValue(UiState.RcResult(weatherData.body()?.hourly?.time, weatherData.body()?.hourly?.temperature2M, weatherData.body()?.hourly?.isDay))

        }
    }
    sealed class UiState {
        class Result (val weatherResponse: MainActivity.Weather): UiState()
       // class RcResult (val rcResultTime: List<String>?, val rcResultTemperature: List<Double>?, val rcResultDay: List<Long>?): UiState()
    }
}