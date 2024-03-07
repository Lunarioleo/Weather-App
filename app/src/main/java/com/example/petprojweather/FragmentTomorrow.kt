package com.example.petprojweather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView


class FragmentTomorrow : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tomorrow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rcView = view.findViewById(R.id.rcView) as RecyclerView
        val viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        viewModel.getData()
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is MyViewModel.UiState.Result -> {
                   // Toast.makeText(context, "${it.weatherResponse.forecast.forecastDay.size}", Toast.LENGTH_SHORT).show()
                    val adapter = RecyclerViewAdapter(it.weatherResponse.forecast.forecastDay[1].hour, "") // 32
                    rcView.adapter = adapter
                }
            }
        }
    }



}