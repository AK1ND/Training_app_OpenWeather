package com.alex_kind.openweathermvvm.fragments.current_weather_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.alex_kind.openweathermvvm.databinding.FragmentCurrentWeatherBinding
import com.alex_kind.openweathermvvm.models.db_weather.WeatherData
import com.alex_kind.openweathermvvm.view_models.DatabaseViewModel
import com.alex_kind.openweathermvvm.view_models.FragmentsViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


class CurrentWeatherFragment : Fragment() {

    private var _bind: FragmentCurrentWeatherBinding? = null
    private val bind get() = _bind!!

    private val coroutineContext = SupervisorJob() + Dispatchers.Main.immediate
    private val coroutineScope: CoroutineScope = CoroutineScope(coroutineContext)

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

            val weatherDB = WeatherData(
                0, "date",  it.name, it.weather[0].description,
                it.main.humidity, it.wind.speed, it.main.temp
            )

            dbViewModel.addWeatherData(weatherDB)
            dbViewModel.updateWeatherData(weatherDB)
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }
}