package com.alex_kind.openweathermvvm.fragments.current_weather_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alex_kind.openweathermvvm.models.current_weather.MainModelCurrentWeather

class CurrentWeatherFragmentViewModel: ViewModel() {

    private val _cityData = MutableLiveData<MainModelCurrentWeather>()
    val cityData: LiveData<MainModelCurrentWeather> get() = _cityData


    fun setData(data: MainModelCurrentWeather){
        _cityData.value = data
    }
}