package com.alex_kind.openweathermvvm.fragments.current_weather_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alex_kind.openweathermvvm.models.current_weather.MainModelCurrentWeather

class CurrentWeatherFragmentViewModel: ViewModel() {

    private val _currentWeatherData = MutableLiveData<MainModelCurrentWeather>()
    val currentWeatherData: LiveData<MainModelCurrentWeather> get() = _currentWeatherData


    fun setData(data: MainModelCurrentWeather){
        _currentWeatherData.value = data
    }
}