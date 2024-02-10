package com.example.petprojweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petprojweather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, Fragment())
            .commit()


    }
}


data class WeatherForecast(val temperature: String)