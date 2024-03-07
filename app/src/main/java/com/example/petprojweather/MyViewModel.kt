package com.example.petprojweather

import android.app.Activity
import android.app.Application

import android.widget.Toast

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
    private val context = MyApplication.getInstance()

    fun getData(){
        viewModelScope.launch {
            val weatherData = async { repo.getWeather() }.await()
            var body = weatherData.body()
            var a = body?.location?.localtime?.substring(10,11)
            when (a) {
                " " -> {
                    a = body?.location?.localtime?.substring(11, 13)
                }
                else -> {
                    a = body?.location?.localtime?.substring(11, 13)
                    Toast.makeText(context, "${a}", Toast.LENGTH_SHORT).show()
                }
            }

            if (body  != null){
                _state.postValue(UiState.Result(body, a))
                //Toast.makeText(context, "${a}", Toast.LENGTH_SHORT).show()
            }

        }
    }
    sealed class UiState {
        class Result (val weatherResponse: WeatherResponse, val time: String?): UiState()
    }
}