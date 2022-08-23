package com.alex_kind.openweathermvvm.fragments.forecast_fragment

import android.annotation.SuppressLint
import android.net.wifi.rtt.CivicLocationKeys.ROOM
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex_kind.openweathermvvm.const.ROOM_DB_DATA
import com.alex_kind.openweathermvvm.databinding.AdapterBinding
import com.alex_kind.openweathermvvm.models.db_weather.WeatherData
import com.alex_kind.openweathermvvm.models.forecast.MainModelForecast
import com.alex_kind.openweathermvvm.models.forecast.Model
import com.alex_kind.openweathermvvm.view_models.DatabaseViewModel
import com.bumptech.glide.Glide
import java.text.DateFormatSymbols
import java.util.*
import kotlin.math.log


@SuppressLint("NotifyDataSetChanged")
class ForecastAdapter(private val dbViewModel: DatabaseViewModel) :
    RecyclerView.Adapter<ForecastAdapter.MainViewHolder>() {

    private var forecastList = mutableListOf<Model>()

    private var dbForecastData = emptyList<WeatherData>()

    fun setForecast(forecast: List<Model>) {
        this.forecastList = forecast.toMutableList()
        notifyDataSetChanged()
    }

    fun setDatabase(db: List<WeatherData>){
        this.dbForecastData = db
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val body = forecastList[position]

        holder.bind.tvRecyclerTemp.text = String.format("%.1f", body.main.temp) + "\u00B0C"
        holder.bind.tvRecyclerDescription.text = body.weather[0].description
        Glide.with(holder.itemView.context)
            .load("https://openweathermap.org/img/wn/${body.weather[0].icon}@2x.png")
            .into(holder.bind.iconWeather)


        //DATE
        val year: Int = body.dt_txt.substring(0, 4).toInt()
        val month = body.dt_txt.substring(5, 7).toInt() - 1
        val day = body.dt_txt.substring(8, 10).toInt()
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val weekday = DateFormatSymbols().weekdays[dayOfWeek]

        holder.bind.tvRecyclerDate.text = weekday + " " + body.dt_txt.substring(10)

        val weather = WeatherData(position+1, "cityName", "cityName", body.weather[0].description,
        body.main.humidity, body.wind.speed, body.main.temp)

        if (dbForecastData.size < 41){
            dbViewModel.addWeatherData(weather)
        } else {
            dbViewModel.updateWeatherData(weather)
        }
    }


    override fun getItemCount(): Int = forecastList.size


    class MainViewHolder(val bind: AdapterBinding) : RecyclerView.ViewHolder(bind.root) {}
}