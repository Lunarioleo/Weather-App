package com.example.petprojweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val time: List<String>, private val temperature: List<Double>, private val isDay: List<Long>): RecyclerView.Adapter<RecyclerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val listItemView = LayoutInflater.from(parent.context).inflate(R.layout.rc_view_item, parent, false)
        return RecyclerViewHolder(listItemView)
    }

    override fun getItemCount(): Int = time.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.frData.text = time[position].substring(11)
        holder.frTemperature.text = temperature[position].toInt().toString() + " °C"
        if (isDay[position].toInt() == 0){
            holder.frImage.setImageResource(R.drawable.night)
        } else {
            holder.frImage.setImageResource(R.drawable.day)
        }
    }
}

class RecyclerViewHolder(item: View): RecyclerView.ViewHolder(item) {
    val frImage = item.findViewById<ImageView>(R.id.fragmentImage)
    val frTemperature = item.findViewById<TextView>(R.id.fragmentTemperature)
    val frData = item.findViewById<TextView>(R.id.fragmentTime)
}





