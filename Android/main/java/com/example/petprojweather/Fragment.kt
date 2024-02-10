package com.example.petprojweather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Fragment : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = arrayListOf<String>()
        repeat(5){
            items.add("Cloudy")
        }


        var myAdapter = RecyclerViewAdapter(items)
        val rcView = view.findViewById<RecyclerView>(R.id.rcView)
        val layoutManager = LinearLayoutManager(context as MainActivity)
        rcView.adapter = myAdapter


    }




}