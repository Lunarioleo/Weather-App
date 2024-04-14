package com.example.petprojweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
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

        val viewModel = ViewModelProvider(activity as ViewModelStoreOwner)[MyViewModel::class.java]

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is MyViewModel.UiState.Result -> {
                    val adapter = RecyclerViewAdapter(it.weatherResponse.forecast.forecastDay[1].hour)
                    rcView.adapter = adapter
                }
                else->{}
            }

        }
    }



}