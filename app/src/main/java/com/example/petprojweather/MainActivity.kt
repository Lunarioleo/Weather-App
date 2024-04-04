package com.example.petprojweather


import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.petprojweather.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.gson.annotations.SerializedName

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding

    companion object {
        lateinit var instance: MainActivity
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        instance = this
        setSupportActionBar(binding.toolbar)
        val viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        viewModel.fetchLocation()
        val toggle = ActionBarDrawerToggle(this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open_nav,
            R.string.close_nav)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navigationDrawer.setNavigationItemSelectedListener(this)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.forecast ->{
                openFragment(WeatherForecastFragment())
            }
            R.id.search->{
                openFragment(SearchCityFragment())
            }
            R.id.savedCities->{
                 openFragment(CitiesList())
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun openFragment(fr: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fr)
            .commit()
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


