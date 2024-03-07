package com.example.petprojweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(private val data: ArrayList<Hourly>, currentTime:String): RecyclerView.Adapter<RecyclerViewHolder>(){

    private var startIndex: Int = 0 // Initialize startIndex with an invalid value // 14

    init {
        if (currentTime.isNotEmpty()){
            startIndex = currentTime.toInt()
        }
    }

    init {
        // Find the index where time matches currentTime
        for (i in 0 until data.size) {
            if (data[i].time == currentTime) {
                startIndex = i
                break
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val listItemView = LayoutInflater.from(parent.context).inflate(R.layout.rc_view_item, parent, false)
        return RecyclerViewHolder(listItemView)
    }

    override fun getItemCount(): Int {
        // Return the count of items from the start index to the end of the list
        return if (startIndex != -1) data.size - startIndex else 0
    }


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        val currentIndex = startIndex.plus(position)
        holder.frTime.text = data[currentIndex].time.substring(10,16)

        val temp = data[currentIndex].tempHourly.toFloat().toInt()
        holder.frTemperature.text = " ${temp}°"

        Picasso.get()
            .load("https:" + data[currentIndex].condition.icon)
            .into(holder.frImage)
    }
}

class RecyclerViewHolder(item: View): RecyclerView.ViewHolder(item) {
    val frImage = item.findViewById<ImageView>(R.id.fragmentImage)
    var frTemperature = item.findViewById<TextView>(R.id.fragmentTemperature)
    val frTime = item.findViewById<TextView>(R.id.fragmentTime)
}





