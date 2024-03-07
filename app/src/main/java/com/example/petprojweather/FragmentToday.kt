package com.example.petprojweather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
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
        val viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        viewModel.getData()
        viewModel.uiState.observe(viewLifecycleOwner) {

            when (it) {
                is MyViewModel.UiState.Result -> {

                    val adapter = RecyclerViewAdapter(it.weatherResponse.forecast.forecastDay[0].hour, it.time.toString())
                    rcView.adapter = adapter
                }
            }

        }
    }}