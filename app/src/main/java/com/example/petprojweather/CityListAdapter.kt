package com.example.petprojweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CityListAdapter(var weatherMap: Map<City, WeatherResponse> = emptyMap(), private val listener: (String)->Unit):
    RecyclerView.Adapter<CityViewholder>() {

    val repo  = MyApplication.getInstance().repo

    var entryList: List<Map.Entry<City, WeatherResponse>> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewholder {
        val listItemView = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        return CityViewholder(listItemView)
    }

    fun updateWeatherList(itemsToUpdate: Map<City, WeatherResponse>) {
        weatherMap = itemsToUpdate
        entryList = weatherMap.entries.toList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = entryList.size

    override fun onBindViewHolder(holder: CityViewholder, position: Int) {

        if (entryList.isEmpty()) {
            return
        }

        val entryKey = entryList[position]

            holder.cancel.setOnClickListener {
                repo.delete(entryKey.key)
            }

            val tee = entryKey.value.location.name

            holder.temperature.text = entryList[position].value.current.temp.toDouble().toInt().toString() + "°"
            holder.cityName.text = entryList[position].value.location.name
            val imageUrl = entryList[position].value.current.currentCondition.icon
        holder.itemView.setOnClickListener {
            listener(tee)
        }

            Picasso.get()
                .load("https:$imageUrl")
                .into(holder.image)
        }
    }

class CityViewholder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val cityName = itemView.findViewById<TextView>(R.id.cityName)
    val temperature = itemView.findViewById<TextView>(R.id.cardTemperature)
    val image = itemView.findViewById<ImageView>(R.id.photo)
    val cancel = itemView.findViewById<ImageView>(R.id.cancel)

}
