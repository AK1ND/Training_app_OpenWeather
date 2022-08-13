package com.alex_kind.openweathermvvm.fragments.current_weather_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.alex_kind.openweathermvvm.databinding.FragmentCurrentWeatherBinding
import com.alex_kind.openweathermvvm.fragments.FragmentsViewModel
import com.bumptech.glide.Glide


class CurrentWeatherFragment : Fragment() {

    private lateinit var _bind: FragmentCurrentWeatherBinding
    private val bind get() = _bind

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

        setParams()
    }

    @SuppressLint("SetTextI18n")
    private fun setParams() {
        fragmentViewModel.currentWeatherData.observe(viewLifecycleOwner) {
            val iconID = it.weather[0].icon
            Glide
                .with(this)
                .load("https://openweathermap.org/img/wn/$iconID@2x.png")
                .into(bind.iconWeatherCurrent)

            bind.tvCityName.text = it.name
            bind.tvDescription.text = it.weather[0].description
            bind.tvHumidity.text = it.main.humidity.toString() + "%"
            bind.tvWind.text = it.wind.speed.toString() + " m/s"
            bind.tvTemp.text = String.format("%.1f", it.main.temp) + "\u00B0C"
        }
    }
}