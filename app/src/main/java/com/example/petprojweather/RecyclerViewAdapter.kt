package com.example.petprojweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Calendar

class RecyclerViewAdapter(private val data: ArrayList<Hourly>, private val isFragmentTomorrow: Boolean = false): RecyclerView.Adapter<RecyclerViewHolder>(){
    private val sdf = SimpleDateFormat("H")
    private var startIndex: Int = 0

    init {
        startIndex = if (isFragmentTomorrow){
            0
        } else {
            sdf.format(Calendar.getInstance().time).toInt()
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val listItemView = LayoutInflater.from(parent.context).inflate(R.layout.rc_view_item, parent, false)
        return RecyclerViewHolder(listItemView)
    }

    override fun getItemCount(): Int {
        val a = data.size
        return a - startIndex
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





