package com.example.petprojweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter (val items: ArrayList<String>): RecyclerView.Adapter<RecyclerViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val listItemView = LayoutInflater.from(parent.context).inflate(R.layout.rc_view_item, parent, false)
        return RecyclerViewHolder(listItemView)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.frImage.setImageResource(R.drawable.night)
        holder.frTemperature.text = items[position]
    }

}

class RecyclerViewHolder(item: View): RecyclerView.ViewHolder(item) {
    val frImage = item.findViewById<ImageView>(R.id.fragmentImage)
    val frTemperature = item.findViewById<TextView>(R.id.fragmentTemperature)
    val frData = item.findViewById<TextView>(R.id.fragmentTime)
}





