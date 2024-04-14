package com.example.petprojweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView


class FragmentToday : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_today_weather, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rcView = view.findViewById(R.id.rcView) as RecyclerView
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(activity as ViewModelStoreOwner)[MyViewModel::class.java]


        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is MyViewModel.UiState.Result -> {
                    val adapter = RecyclerViewAdapter(it.weatherResponse.forecast.forecastDay[0].hour)
                    rcView.adapter = adapter
                }
                else->{}
            }

        }
    }}