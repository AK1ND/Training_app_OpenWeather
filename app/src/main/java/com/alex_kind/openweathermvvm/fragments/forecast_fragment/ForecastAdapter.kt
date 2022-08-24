package com.alex_kind.openweathermvvm.fragments.forecast_fragment

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex_kind.openweathermvvm.const.ROOM_DB_DATA
import com.alex_kind.openweathermvvm.databinding.AdapterBinding
import com.alex_kind.openweathermvvm.db.GetBitmap
import com.alex_kind.openweathermvvm.models.db_weather.WeatherData
import com.alex_kind.openweathermvvm.models.forecast.Model
import com.alex_kind.openweathermvvm.view_models.DatabaseViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.text.DateFormatSymbols
import java.util.*


@SuppressLint("NotifyDataSetChanged")
class ForecastAdapter(private val dbViewModel: DatabaseViewModel, private val context: Context) :
    RecyclerView.Adapter<ForecastAdapter.MainViewHolder>() {

    private val coroutineContext = SupervisorJob() + Dispatchers.Main.immediate
    private val coroutineScope: CoroutineScope = CoroutineScope(coroutineContext)

    private var forecastList = mutableListOf<Model>()

    private var dbForecastData = mutableListOf<WeatherData>()

    fun setForecast(forecast: List<Model>) {
        this.forecastList = forecast.toMutableList()
        notifyDataSetChanged()
    }

    fun setDatabaseForInput(db: List<WeatherData>) {
        dbForecastData = db.toMutableList()
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


        coroutineScope.launch {
            try {

                val bitmap = GetBitmap(context).bitmap(body.weather[0].icon)

                val weather = WeatherData(
                    position + 1, bitmap, "$weekday ${body.dt_txt.substring(10)}",
                    "cityName", body.weather[0].description,
                    body.main.humidity, body.wind.speed, body.main.temp
                )

                if (dbForecastData.size < 41) {
                    dbViewModel.addWeatherData(weather)
                } else {
                    dbViewModel.updateWeatherData(weather)
                }
            } catch (e: Exception) {
                Log.d(ROOM_DB_DATA, "ERROR")
            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }


    override fun getItemCount(): Int = forecastList.size


    class MainViewHolder(val bind: AdapterBinding) : RecyclerView.ViewHolder(bind.root) {}
}