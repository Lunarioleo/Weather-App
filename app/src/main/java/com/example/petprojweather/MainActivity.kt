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

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MyViewModel
    companion object {
        lateinit var instance: MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        instance = this
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val toggle = ActionBarDrawerToggle(this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open_nav,
            R.string.close_nav)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        viewModel.fetchLocation()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.forecast ->{
                openFragment(WeatherForecastFragment())
                viewModel.fetchLocation()
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





