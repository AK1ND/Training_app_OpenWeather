package com.alex_kind.openweathermvvm.fragments.forecast_fragment

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alex_kind.openweathermvvm.const.ROOM_DB_DATA
import com.alex_kind.openweathermvvm.databinding.AdapterBinding
import com.alex_kind.openweathermvvm.models.db_weather.WeatherData

class ForecastAdapterDatabase() : RecyclerView.Adapter<ForecastAdapterDatabase.MainViewHolder>() {

    private var dbForecast = mutableListOf<WeatherData>()

    @SuppressLint("NotifyDataSetChanged")
    fun setDatabaseForOutput(db: List<WeatherData>) {
        this.dbForecast = db.toMutableList()
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        try {
            val pos = position + 1
            holder.bind.tvRecyclerDate.text = dbForecast[pos].date
            holder.bind.tvRecyclerTemp.text = dbForecast[pos].temp.toString()
            holder.bind.tvRecyclerDescription.text = dbForecast[pos].description
            holder.bind.iconWeather.load(dbForecast[pos].icon)
        } catch (e: Exception) {
            Log.d(ROOM_DB_DATA, "stop")
        }


    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun getItemCount(): Int = dbForecast.size


    class MainViewHolder(val bind: AdapterBinding) : RecyclerView.ViewHolder(bind.root)

}