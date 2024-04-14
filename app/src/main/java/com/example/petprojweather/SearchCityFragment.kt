package com.example.petprojweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.petprojweather.databinding.FragmentSearchCityBinding


class SearchCityFragment : Fragment() {
    private lateinit var binding: FragmentSearchCityBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentSearchCityBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(activity as ViewModelStoreOwner)[MyViewModel::class.java]




        binding.searchBtn.setOnClickListener {
            val cityName = binding.search.text.toString()
            viewModel.addCity(cityName)
        }


    }


}