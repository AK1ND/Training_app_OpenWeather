package com.alex_kind.openweathermvvm.fragments.current_weather_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.alex_kind.openweathermvvm.const.ROOM_DB_DATA
import com.alex_kind.openweathermvvm.databinding.FragmentCurrentWeatherBinding
import com.alex_kind.openweathermvvm.db.GetBitmap
import com.alex_kind.openweathermvvm.models.db_weather.WeatherData
import com.alex_kind.openweathermvvm.view_models.DatabaseViewModel
import com.alex_kind.openweathermvvm.view_models.FragmentsViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CurrentWeatherFragment : Fragment() {

    private var _bind: FragmentCurrentWeatherBinding? = null
    private val bind get() = _bind!!


    private lateinit var dbViewModel: DatabaseViewModel

    private val fragmentViewModel: FragmentsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        return bind.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbViewModel = ViewModelProvider(this)[DatabaseViewModel::class.java]
        setParams()
        load()
    }

    private fun load() {
        fragmentViewModel.errorLoading.observe(viewLifecycleOwner) {
            if (it) {
                setParamsFromDatabase()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setParamsFromDatabase() {
        dbViewModel.readAllWeatherData.observe(viewLifecycleOwner) {
            try {
                val body = it[0]
                bind.tvCityName.text = body.cityName
                bind.iconWeatherCurrent.load(body.icon)
                bind.tvTemp.text = body.temp.toString() + "\u00B0C"
                bind.tvDescription.text = body.description
                bind.tvWind.text = body.wind.toString() + " m/s"
                bind.tvHumidity.text = body.humidity.toString() + "%"

            } catch (e: Exception) {
                Log.d(ROOM_DB_DATA, "CURRENT WEATHER DB ERROR")
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setParams() {
        fragmentViewModel.currentWeatherData.observe(viewLifecycleOwner) {
            val iconID = it.weather[0].icon
            Glide
                .with(bind.iconWeatherCurrent.context)
                .load("https://openweathermap.org/img/wn/$iconID@2x.png")
                .into(bind.iconWeatherCurrent)

            bind.tvCityName.text = it.name
            bind.tvDescription.text = it.weather[0].description
            bind.tvHumidity.text = it.main.humidity.toString() + "%"
            bind.tvWind.text = it.wind.speed.toString() + " m/s"
            bind.tvTemp.text = String.format("%.1f", it.main.temp) + "\u00B0C"

            lifecycleScope.launch {
                delay(2000)
                val bitmap = GetBitmap(requireContext()).bitmap(iconID)

                val weatherDB = WeatherData(
                    0, bitmap, "date", it.name, it.weather[0].description,
                    it.main.humidity, it.wind.speed, it.main.temp
                )

                dbViewModel.addWeatherData(weatherDB)
                dbViewModel.updateWeatherData(weatherDB)
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }
}