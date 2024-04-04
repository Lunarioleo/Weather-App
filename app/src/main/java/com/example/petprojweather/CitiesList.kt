package com.example.petprojweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.petprojweather.databinding.FragmentCitiesListBinding


class CitiesList : Fragment() {
    private lateinit var binding: FragmentCitiesListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCitiesListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(activity as ViewModelStoreOwner)[MyViewModel::class.java]


        var adapter  = CityListAdapter(emptyMap()){
            viewModel.getData(it)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer,WeatherForecastFragment())
                .commit()
        }
        
        binding.citiesRcView.adapter = adapter

        viewModel.listState.observe(viewLifecycleOwner) {
            when (it) {
                is MyViewModel.ListState.EmptyList -> Unit

                is MyViewModel.ListState.UpdatedList -> {

                    viewModel.getListForecast(it.list)
                }
            }
        }


        viewModel.uiState.observe(viewLifecycleOwner) { a ->
            when (a) {
                is MyViewModel.UiState.Result -> {

                }
                is MyViewModel.UiState.ResultCards -> {
                    adapter.updateWeatherList(a.weatherResponses)

                }
            }
        }
    }
}


