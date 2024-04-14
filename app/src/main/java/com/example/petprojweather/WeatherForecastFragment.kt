package com.example.petprojweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewpager2.widget.ViewPager2
import com.example.petprojweather.databinding.FragmentWeatherForecastBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt


class WeatherForecastFragment : Fragment() {
    private lateinit var binding: FragmentWeatherForecastBinding
    private lateinit var fragmentAdapter: VPAdapter

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var myViewModel: MyViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentWeatherForecastBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myViewModel = ViewModelProvider(activity as ViewModelStoreOwner)[MyViewModel::class.java]

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())


        fragmentAdapter = VPAdapter(parentFragmentManager, lifecycle)
        binding.viewPager.adapter = fragmentAdapter
        binding.viewPager.isUserInputEnabled = false




        binding.location.setOnClickListener {
            myViewModel.startAnimation(binding.location)
           // myViewModel.checkPermissions()
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

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })

        myViewModel.uiState.observe(viewLifecycleOwner) {
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
                    binding.feelingTemperature.text =
                        it.weatherResponse.current.feelsLikeTemp.toDouble().toInt().toString() + "°"
                    binding.windSpeed.text = it.weatherResponse.current.windSpeed + "km/h"
                    binding.humidity.text = it.weatherResponse.current.humidity + "%"
                    binding.uvIndex.text = it.weatherResponse.current.uv
                }

                else -> {}
            }

        }


    }




}